package com.bantads.gerente.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.model.GerenteDTO;
import com.bantads.gerente.repository.GerenteRepository;

@CrossOrigin
@RestController
public class GerenteREST {
	public static List<Gerente> lista = new ArrayList<>();
	@Autowired
	private GerenteRepository repo;
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/gerente")
	ResponseEntity<Object> listarTodos() {
		List<Gerente> lista = repo.findAll();
		// Converte lista de Entity para lista DTO
		List<GerenteDTO> gerentes = lista.stream().map(e -> mapper.map(e, GerenteDTO.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(new JsonResponse(true, "", gerentes), HttpStatus.OK);
	}

	@GetMapping("/gerente/{email}")
	public ResponseEntity<Object> obterTodosGerentes(@PathVariable("email") String email) {
		Gerente g = repo.findByEmail(email);

		if (g == null) {
			return new ResponseEntity<>(new JsonResponse(false, "Gerente não encontrado", null), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new JsonResponse(true, "Gerente encontrado", mapper.map(g, GerenteDTO.class)),
				HttpStatus.OK);
	}

	@PostMapping("/gerente")
	public ResponseEntity<Object> inserir(@RequestBody GerenteDTO gerente) {
		// salva a Entidade convertida do DTO
		repo.save(mapper.map(gerente, Gerente.class));
		// busca o usuário inserido
		Gerente ger = repo.findByCpf(gerente.getCpf());
		// // retorna o DTO equivalente à entidade
		// return mapper.map(ger, GerenteDTO.class);

		return new ResponseEntity<>(
				new JsonResponse(true, "Gerente inserido com sucesso", mapper.map(ger, GerenteDTO.class)),
				HttpStatus.OK);
	}

	@PutMapping("/gerente/{id}")
	public ResponseEntity<Object> alterarGerente(@PathVariable("id") int id, @RequestBody Gerente gerente) {
		Gerente g = repo.findById(id).get();
		if (g != null) {
			g.setNome(gerente.getNome());
			g.setCpf(gerente.getCpf());
			g.setEmail(gerente.getEmail());
			g.setPassword(gerente.getPassword());
			repo.save(g);
			return new ResponseEntity<>(new JsonResponse(true, "Gerente alterado com sucesso", g), HttpStatus.OK);
		}
		return new ResponseEntity<>(new JsonResponse(false, "Gerente não encontrado", null), HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/gerente/{id}")
	public ResponseEntity<Object> removerGerente(@PathVariable("id") int id) {
		Gerente g = repo.findById(id).get();
		if (g != null) {
			repo.deleteById(g.getId());
			return new ResponseEntity<>(new JsonResponse(true, "Gerente removido com sucesso", null), HttpStatus.OK);
		}
		return new ResponseEntity<>(new JsonResponse(false, "Gerente não encontrado", null), HttpStatus.NOT_FOUND);

	}

}
