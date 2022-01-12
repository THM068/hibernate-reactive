package com.chess.player.reactive_hibernate_starter.dao;
import com.chess.player.reactive_hibernate_starter.model.dto.FindAllRequest;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface Repository<T> {

  CompletionStage<List<T>> findAll(FindAllRequest findAllRequest);
}
