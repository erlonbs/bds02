package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ControllerNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	
	public List <EventDTO> findAll(){
		List<Event> lista = repository.findAll(Sort.by("name"));
		return lista.stream().map(x -> new EventDTO(x)).collect(Collectors.toList());
	}



	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
		EventDTO city;			
		Event entity = repository.getOne(id);
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
	    entity.setCity(new City(dto.getCityId(),null)); 
		entity = repository.save(entity);
		return new EventDTO(entity);
	}
	catch (EntityNotFoundException e) {
		throw new ControllerNotFoundException(" id not found " + id);
	}
	
	}
}
