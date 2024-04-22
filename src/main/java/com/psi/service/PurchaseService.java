package com.psi.service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.model.dto.EmployeeDto;
import com.psi.model.dto.ProductDto;
import com.psi.model.dto.PurchaseDto;
import com.psi.model.dto.PurchaseItemDto;
import com.psi.model.dto.SupplierDto;
import com.psi.model.po.Employee;
import com.psi.model.po.Purchase;
import com.psi.model.po.PurchaseItem;
import com.psi.model.po.Supplier;
import com.psi.repository.PurchaseItemRepository;
import com.psi.repository.PurchaseRepository;

import jakarta.transaction.Transactional;

@Service
public class PurchaseService {
	@Autowired
	private PurchaseRepository purchaseRepository;

	@Autowired
	private PurchaseItemRepository purchaseItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	// 採購單-----------------------------------------------
	// 新增
	@Transactional
	public Long add(PurchaseDto purchaseDto) {
		Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
		Purchase savePurchase = purchaseRepository.saveAndFlush(purchase);
		return savePurchase.getId();// 返回保存後的 ID
	}

	// 修改
	@Transactional
	public void update(PurchaseDto purchaseDto, Long id) {
		Optional<Purchase> purchaseOpt = purchaseRepository.findById(id);
		if (purchaseOpt.isPresent()) {
			Purchase purchase = purchaseOpt.get();
			// 修改日期
			purchase.setDate(purchaseDto.getDate());
			// 修改員工
			Employee employee = modelMapper.map(purchaseDto.getEmployee(), Employee.class);
			purchase.setEmployee(employee);
			// 修改供應商
			Supplier supplier = modelMapper.map(purchaseDto.getSupplier(), Supplier.class);
			purchase.setSupplier(supplier);

			purchaseRepository.save(purchase);

		}
	}

	// 刪除
	@Transactional
	public void delete(Long id) {
		Optional<Purchase> purchaseOpt = purchaseRepository.findById(id);
		if (purchaseOpt.isPresent()) {
//			purchaseRepository.delete(purchaseOpt.get());
			purchaseRepository.deleteById(id);
		}
	}

	// 查詢-單筆
	public PurchaseDto getPurchaseDtoById(Long id) {
		Optional<Purchase> purchaseOpt = purchaseRepository.findById(id);
		if (purchaseOpt.isPresent()) {
			Purchase purchase = purchaseOpt.get();// po 取得optional取得的資料
//			PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
			// 每一筆po逐一轉換dto
			PurchaseDto purchaseDto = new PurchaseDto();
			purchaseDto.setId(purchase.getId());
			purchaseDto.setDate(purchase.getDate());
			// 因為要轉換給purchaseDto 裡面的資料(employee,supplier)也是dto也要透過modelmapper從po轉dto
			purchaseDto.setEmployee(modelMapper.map(purchase.getEmployee(), EmployeeDto.class));
			purchaseDto.setSupplier(modelMapper.map(purchase.getSupplier(), SupplierDto.class));
			// set 在dto中private Set<PurchaseItemDto> purchaseItems = new LinkedHashSet<>();
			Set<PurchaseItemDto> purchaseItems = new LinkedHashSet<>();
			// 透過迴圈尋訪 並透過 PurchaseItemDto 取得 purchase(po)資料
			for (PurchaseItem item : purchase.getPurchaseItems()) {
				PurchaseItemDto purchaseItemDto = new PurchaseItemDto();
				purchaseItemDto.setId(item.getId());
				purchaseItemDto.setAmount(item.getAmount());
				purchaseItemDto.setProduct(modelMapper.map(item.getProduct(), ProductDto.class));
				// set新增方法add() 將透過 PurchaseItemDto 取得 purchase(po)資料新增至 set物件
				purchaseItems.add(purchaseItemDto);
			}
			// 每一筆po逐一轉換dto
			purchaseDto.setPurchaseItems(purchaseItems);
			return purchaseDto;
		}
		return null;
	}

	// 查詢-多筆(全部)
	public List<PurchaseDto> findAll() {
		List<Purchase> purchases = purchaseRepository.findAll();
		List<PurchaseDto> purchaseDtos = purchases.stream()
				 .map(purchase -> getPurchaseDtoById(purchase.getId()))
				 .toList();
		// 參照service.txt
//		List<PurchaseDto> purchaseDtos = purchases.stream()
//				.map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
//				.toList();
//		List<PurchaseDto> purchaseDtos = purchases.stream().map(purchase -> {
//			PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
//			// 將PersistentSet轉換為普通的Set
//			Set<PurchaseItemDto> purchaseItemDtos = new HashSet<>();
//			for (PurchaseItem purchaseItem : purchase.getPurchaseItems()) {
//				purchaseItemDtos.add(modelMapper.map(purchaseItem, PurchaseItemDto.class));
//			}
//			purchaseDto.setPurchaseItems(purchaseItemDtos);
//			return purchaseDto;
//		}).toList();
		return purchaseDtos;
	}

	// 採購單明細---------------------------------------------
	// 新增
	@Transactional
	// pid:採購單主檔的id序號
	public void addPurchaseItem(PurchaseItemDto purchaseItemDto, Long pid) {
		// 採購單明細po
		PurchaseItem purchaseItem = modelMapper.map(purchaseItemDto, PurchaseItem.class);
		// 採購單(主檔)po
		Purchase purchase = purchaseRepository.findById(pid).get();
		// 採購單明細po與採購單(主檔)po 建立關聯(ps:由多的一方建立關聯)
		purchaseItem.setPurchase(purchase);
		// 儲存
		purchaseItemRepository.save(purchaseItem);
	}

	// 修改
	@Transactional
	// iid:採購單明細的id序號
	public void updatePurchaseItem(PurchaseItemDto purchaseItemDto, Long iid) {
		Optional<PurchaseItem> purchaseItemOpt = purchaseItemRepository.findById(iid);
		if (purchaseItemOpt.isPresent()) {
			// 取得採購單主黨的id 序號
			Long pid = purchaseItemOpt.get().getPurchase().getId();
			// 採購單明細po
			PurchaseItem purchaseItem = modelMapper.map(purchaseItemDto, PurchaseItem.class);
			// 採購單(主檔)po
			Purchase purchase = purchaseRepository.findById(pid).get();
			// 採購單明細po與採購單(主檔)po 建立關聯(ps:由多的一方建立關聯)
			purchaseItem.setPurchase(purchase);
			// 修改
			purchaseItemRepository.save(purchaseItem);// 若 purchaseItem有id則.save()方法會進行模式修改, 若無則進行新增模式
		}
	}

	// 刪除
	@Transactional
	// iid:採購單明細的id
	public void deletePurchaseItem(Long iid) {
		Optional<PurchaseItem> purchaseItemOpt = purchaseItemRepository.findById(iid);
		if (purchaseItemOpt.isPresent()) {
			purchaseItemRepository.deleteById(iid);
		}
	}

	// 查詢-單筆
	public PurchaseItemDto getPurchaseItemDtoById(Long id) {
		Optional<PurchaseItem> purchaseItemOpt = purchaseItemRepository.findById(id);
		if (purchaseItemOpt.isPresent()) {
			PurchaseItem purchaseItem = purchaseItemOpt.get();
//			PurchaseItemDto purchaseItemDto = modelMapper.map(purchaseItem, PurchaseItemDto.class);
			PurchaseItemDto purchaseItemDto = new PurchaseItemDto();
			purchaseItemDto.setId(purchaseItem.getId());
			purchaseItemDto.setAmount(purchaseItem.getAmount());
			purchaseItemDto.setProduct(modelMapper.map(purchaseItem.getProduct(), ProductDto.class));
			return purchaseItemDto;
		}
		return null;
	}
}
