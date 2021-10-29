alter table transacoes add imposto decimal(18,2);

update transacoes set imposto = ((preco * quantidade) * 0.15) where tipo = 'VENDA' and ((preco * quantidade) > 20000);

update transacoes set imposto = 0 where imposto is null;