import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpresaTest {

	private Empresa empresa;

	@BeforeEach
	void setUp() {
		empresa = new Empresa(1L, "EmpresaTest", Arrays.asList(new Oferta()));
	}

	@Test
	void getId() {
		assertEquals(1L, empresa.getId());
	}

	@Test
	void setId() {
		empresa.setId(2L);
		assertEquals(2L, empresa.getId());
	}

	@Test
	void getName() {
		assertEquals("EmpresaTest", empresa.getName());
	}

	@Test
	void setName() {
		empresa.setName("NuevaEmpresa");
		assertEquals("NuevaEmpresa", empresa.getName());
	}

	@Test
	void getOfertas() {
		assertEquals(1, empresa.getOfertas().size());
	}

	@Test
	void setOfertas() {
		Oferta nuevaOferta = mock(Oferta.class);
		empresa.setOfertas(Arrays.asList(nuevaOferta));
		assertEquals(1, empresa.getOfertas().size());
	}

	@Test
	void testEquals() {
		Empresa otraEmpresa = new Empresa(1L, "EmpresaTest", Arrays.asList(new Oferta()));
		assertEquals(empresa, otraEmpresa);
	}

	@Test
	void testHashCode() {
		Empresa otraEmpresa = new Empresa(1L, "EmpresaTest", Arrays.asList(new Oferta()));
		assertEquals(empresa.hashCode(), otraEmpresa.hashCode());
	}

	@Test
	void testToString() {
		assertNotNull(empresa.toString());
	}
}
class OfertaTest {

}

