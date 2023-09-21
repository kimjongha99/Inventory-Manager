    package com.springboot.inventory.supply.dto;

    import com.springboot.inventory.common.entity.Supply;
    import com.springboot.inventory.common.entity.User;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import java.time.LocalDateTime;

    @NoArgsConstructor
    @Getter
    @Setter
    public class SupplyResponseDto {
        private Long supplyId;
        private String categoryName; // 분류
        private String modelName;
        private String serialNum;
        private int amount;
        private String username; //사용자
        private LocalDateTime createdAt;
        private String status;

        private static final Logger logger = LoggerFactory.getLogger(SupplyResponseDto.class);

        public static SupplyResponseDto fromSupply(Supply supply) {
            SupplyResponseDto dto = new SupplyResponseDto();
            dto.setCreatedAt(supply.getCreatedAt());
            dto.setSupplyId(supply.getSupplyId());
            dto.setCategoryName(supply.getCategory().getCategoryName());
            dto.setModelName(supply.getModelName());
            dto.setSerialNum(supply.getSerialNum());
            dto.setAmount(supply.getAmount());
            if (supply.getUser() != null && supply.getUser().getUsername() != null) {
                dto.setUsername(supply.getUser().getUsername());
            } else {
                dto.setUsername(null);  // 또는 기본값을 설정해도 됩니다.
            }
            dto.setStatus(supply.getStatus().toString());
            // createdAt와 다른 필드들도 필요한 경우 여기에 추가
            logger.info("SupplyResponseDto username: {}", dto.getUsername());
            return dto;
        }

    }
