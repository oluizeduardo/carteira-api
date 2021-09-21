package br.com.carteira.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import br.com.carteira.model.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoFormDTO {

	@NotNull
	@NotEmpty
	@Size(min=5, max = 6, message = "Ticker should be between 5 and 6 chars.")
	private String ticker;
	
	@DecimalMin(value = "0.01")
    @Digits(integer=3, fraction=2)
	private BigDecimal preco;
	
	@Min(1)
	private int quantidade;
	
	@NotNull
	private TipoTransacao tipo;
	
	@PastOrPresent(message = "The date must be present or past.")
	private LocalDate data;

}
