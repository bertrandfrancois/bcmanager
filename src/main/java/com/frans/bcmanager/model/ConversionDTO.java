package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.InvoiceCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDTO {

    private Project project;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @NotNull
    private String code;

    public ConversionDTO(String code) {
        this.code = code;
    }

    @InvoiceCode
    public String getCode() {
        return code;
    }
}
