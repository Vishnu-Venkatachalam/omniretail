package com.cognizant.omni.omniretail.service;

import com.cognizant.omni.omniretail.model.Promotion;
import com.cognizant.omni.omniretail.model.enums.PromotionStatus;
import com.cognizant.omni.omniretail.repository.PromotionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepo promotionRepo;

    private void validateDates(Promotion p) {
        if (p.getStartDate() == null || p.getEndDate() == null) {
            throw new RuntimeException("startDate and endDate are required");
        }
        if (p.getStartDate().isAfter(p.getEndDate())) {
            throw new RuntimeException("startDate cannot be after endDate");
        }
    }

    @Transactional
    public Promotion create(Promotion p) {
        if (p.getName() == null || p.getName().isBlank()) {
            throw new RuntimeException("Promotion name is required");
        }
        if (promotionRepo.existsByNameIgnoreCase(p.getName())) {
            throw new RuntimeException("Promotion name already exists");
        }
        validateDates(p);

        if (p.getStatus() == null) {
            p.setStatus(PromotionStatus.ACTIVE);
        }
        return promotionRepo.save(p);
    }

    public Promotion getById(Long id) {
        return promotionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
    }

    public List<Promotion> getAll() {
        return promotionRepo.findAll();
    }

    @Transactional
    public Promotion update(Long id, Promotion updated) {
        Promotion existing = getById(id);

        // If name is changing, ensure uniqueness
        if (updated.getName() != null && !updated.getName().equalsIgnoreCase(existing.getName())) {
            if (promotionRepo.existsByNameIgnoreCase(updated.getName())) {
                throw new RuntimeException("Promotion name already exists");
            }
            existing.setName(updated.getName());
        }

        if (updated.getDiscountType() != null) {
            existing.setDiscountType(updated.getDiscountType());
        }
        if (updated.getDiscountValue() != null) {
            existing.setDiscountValue(updated.getDiscountValue());
        }
        if (updated.getStartDate() != null) {
            existing.setStartDate(updated.getStartDate());
        }
        if (updated.getEndDate() != null) {
            existing.setEndDate(updated.getEndDate());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }

        // Validate final dates after applying changes
        validateDates(existing);

        return promotionRepo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Promotion existing = getById(id);
        promotionRepo.delete(existing);
    }

    @Transactional
    public Promotion changeStatus(Long id, PromotionStatus status) {
        Promotion p = getById(id);
        p.setStatus(status);
        return promotionRepo.save(p);
    }
}