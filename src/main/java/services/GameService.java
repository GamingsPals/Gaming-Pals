package services;

import domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.GameRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {


    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll(){
        return new ArrayList<>(gameRepository.findAll());
    }

}
