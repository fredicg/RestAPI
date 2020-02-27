alter table foodapi.restaurante add ativo tinyint(1) not null;
update foodapi.restaurante set ativo = true;