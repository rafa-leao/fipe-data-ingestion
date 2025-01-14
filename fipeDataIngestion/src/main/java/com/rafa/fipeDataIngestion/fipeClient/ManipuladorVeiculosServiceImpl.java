package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafa.fipeDataIngestion.database.entity.VeiculoEntity;
import com.rafa.fipeDataIngestion.database.repository.VeiculoRepository;
import com.rafa.fipeDataIngestion.fipeClient.interfaces.ManipuladorVeiculosFIPE;
import com.rafa.fipeDataIngestion.fipeClient.model.Veiculo;

@Service
public class ManipuladorVeiculosServiceImpl implements ManipuladorVeiculosFIPE {
    private @Autowired VeiculoRepository veiculoRepository;
    
    @Override
    public List<Veiculo> buscaVeiculosSalvos(String marcaCode) {
        List<VeiculoEntity> veiculoEntities = veiculoRepository.findByMarcaCode(marcaCode);
        return VeiculoEntity.toVeiculoList(veiculoEntities);
    }

    @Override
    public Optional<Veiculo> atualizaVeiculoPorId(String veiculoId, Veiculo veiculo) {
        Optional<VeiculoEntity> veiculoSalvo = veiculoRepository.findByCode(veiculoId);
        if (!veiculoSalvo.isPresent())
            return Optional.empty();

        VeiculoEntity veiculoEntity = veiculoSalvo.get();
        veiculoEntity.setName(veiculo.getName());
        veiculoEntity.setObservation(veiculo.getObservation());
        veiculoRepository.save(veiculoEntity);

        return Optional.of(veiculo);
    }
}
