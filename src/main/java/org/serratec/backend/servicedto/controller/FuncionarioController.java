package org.serratec.backend.servicedto.controller;

import java.io.IOException;
import java.util.List;

import org.serratec.backend.servicedto.domain.Foto;
import org.serratec.backend.servicedto.domain.Funcionario;
import org.serratec.backend.servicedto.dto.FuncionarioDTO;
import org.serratec.backend.servicedto.dto.FuncionarioSalarioDTO;
import org.serratec.backend.servicedto.repository.FuncionarioRepository;
import org.serratec.backend.servicedto.service.FotoService;
import org.serratec.backend.servicedto.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private FotoService fotoService;
	
	@GetMapping
	public ResponseEntity<List<FuncionarioDTO>> listar() {
		return ResponseEntity.ok(funcionarioService.listar());
	}
	
	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
		Foto foto = fotoService.buscarPorIdFuncionario(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, foto.getTipo());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FuncionarioDTO> buscar(@PathVariable Long id) {
		FuncionarioDTO funcionario = funcionarioService.buscar(id);
		if (null == funcionario) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(funcionario);
	}
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<FuncionarioDTO> inserir(@RequestPart MultipartFile file, 
			@RequestPart Funcionario funcionario) throws IOException {
		return ResponseEntity.ok(funcionarioService.inserir(funcionario, file));
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	
	/*
	@GetMapping
	public ResponseEntity<List<Funcionario>> listar() {
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		return ResponseEntity.ok(funcionarios);
	}
	*/
	
	@GetMapping("/pagina")
	public ResponseEntity<Page<Funcionario>> listarPaginado(
			@PageableDefault(sort="id", direction=Sort.Direction.ASC, page=0, size=8)
			Pageable pageable) {
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		return ResponseEntity.ok(funcionarios);
	}
	
	@GetMapping("/salario")
	public ResponseEntity<Page<Funcionario>> listarSalarios(
			@RequestParam(defaultValue = "0") Double valorMinimo,
			@RequestParam(defaultValue = "10000") Double valorMaximo,
			Pageable pageable) {
		Page<Funcionario> funcionarios = funcionarioRepository.buscarSalario(valorMinimo, valorMaximo, pageable);
		return ResponseEntity.ok(funcionarios);
	}
	
	@GetMapping("/nome")
	public ResponseEntity<Page<Funcionario>> buscarPorNome(
			@RequestParam(defaultValue = "a") String paramNome,
			Pageable pageable) {
		Page<Funcionario> funcionarios = funcionarioRepository.buscarPorNome(paramNome, pageable);
		return ResponseEntity.ok(funcionarios);
	}
	
	@GetMapping("/salarios-por-idade")
	public ResponseEntity<List<FuncionarioSalarioDTO>> buscarSalariosPorIdade() {
		return ResponseEntity.ok(funcionarioRepository.buscarSalariosPorIdade());
	}
	
}
