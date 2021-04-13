package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)

  describe("Vendedor fijo") {
    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)

    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }
  }

  describe("Viajante") {
    val cordoba = Provincia(2000000)
    val villaDolores = Ciudad(cordoba)
    val viajante = Viajante(listOf(misiones))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }
  }

  describe("Comercio Corresponsal") {
    val cordoba = Provincia(2000000)
    val villaDolores = Ciudad(cordoba)
    val comercio = ComercioCorresponsal(listOf(sanIgnacio))

    describe("puedeTrabajarEn") {
      it("una ciudad en la que tiene sucursal") {
        comercio.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad en la que no tiene sucursal") {
        comercio.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }
  }
  describe("es versatil") {
    val certificacion = Certificacion(true,20)
    val certificacion2 = Certificacion(true,5)
    val certificacion3 = Certificacion(false,30)
    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)

    describe("un vendedor es versatil") {
      it("false") {
        vendedorFijo.agregarCertificacion(certificacion)
        vendedorFijo.esVersatil().shouldBeFalse()
      }
      it("true") {
        vendedorFijo.agregarCertificacion(certificacion2)
        vendedorFijo.agregarCertificacion(certificacion3)
        vendedorFijo.esVersatil().shouldBeTrue()
      }
    }
  }

  describe("Un vendedor es firme") {
    val vendedorFijo = VendedorFijo(sanIgnacio)
    val certificacion = Certificacion(true,5)
    val certificacion2 = Certificacion(false,30)

    describe("esFirme") {
      it("puntaje menor a 30") {
        vendedorFijo.agregarCertificacion(certificacion)
        vendedorFijo.esFirme().shouldBeFalse()
      }
      it("puntaje mayor o igual a 30") {
        vendedorFijo.agregarCertificacion(certificacion2)
        vendedorFijo.esFirme().shouldBeTrue()
      }
    }
  }


  describe("esInfluyente") {
    val santaFe = Provincia(1500000)
    val entreRios = Provincia(1600000)
    val buenosAires = Provincia(6000000)
    val cordoba = Provincia(2000000)
    val chivilcoy = Ciudad(buenosAires)
    val bragado = Ciudad(buenosAires)
    val lobos = Ciudad(buenosAires)
    val pergamino = Ciudad(buenosAires)
    val zarate = Ciudad(buenosAires)
    val rosario = Ciudad(santaFe)
    val rafaela = Ciudad(santaFe)
    val sanFrancisco = Ciudad(cordoba)
    val diamante = Ciudad(entreRios)
    val amstrong = Ciudad(santaFe)

    val comercio = ComercioCorresponsal(listOf(chivilcoy,bragado,lobos,pergamino,zarate))
    val comercio2 = ComercioCorresponsal(listOf(rosario,rafaela,sanFrancisco,diamante))
    val comercio3 = ComercioCorresponsal(listOf(rosario,rafaela,amstrong,diamante))

    describe("influyente"){
      it("5 ciudades") {
        comercio.esInfluyente().shouldBeTrue()
      }
      it("3 provincias") {
        comercio2.esInfluyente().shouldBeTrue()
      }
      it("no influyente") {
        comercio3.esInfluyente().shouldBeFalse()
      }
    }
  }

  describe("Centro de Distribucion") {
    val vendedorMayorPuntaje = VendedorFijo(sanIgnacio)
    val vendedorMenorPuntaje = VendedorFijo(sanIgnacio)
    val certificacion = Certificacion(true,100)
    val certificacion2 = Certificacion(false,50)
    vendedorMayorPuntaje.agregarCertificacion(certificacion)
    vendedorMenorPuntaje.agregarCertificacion(certificacion2)
    val centro = CentroDistribucion(sanIgnacio)
    centro.agregarVendedor(vendedorMayorPuntaje)
    centro.agregarVendedor(vendedorMenorPuntaje)

    it("vendedorMayorPuntaje") {
      centro.vendedorEstrella().shouldBe(vendedorMayorPuntaje)
    }
  }

  describe("puede cubir una ciudad") {
    val vendedor = VendedorFijo(sanIgnacio)
    val yapeyu = Ciudad(misiones)
    val centro = CentroDistribucion(sanIgnacio)
    centro.agregarVendedor(vendedor)

    it("no puede cubir") {
      centro.puedeCubrir(yapeyu).shouldBeFalse()
    }
    it("puede cubrir") {
      centro.puedeCubrir(sanIgnacio).shouldBeTrue()
    }
  }

  describe("vendedores genericos") {
    val vendedor = VendedorFijo(sanIgnacio)
    val vendedor2 = VendedorFijo(sanIgnacio)
    val certificacion = Certificacion(true,12)
    val certificacion2 = Certificacion(false,5)
    vendedor.agregarCertificacion(certificacion)
    vendedor2.agregarCertificacion(certificacion2)
    val centro = CentroDistribucion(sanIgnacio)
    centro.agregarVendedor(vendedor)

    it("coleccion vacia") {
      centro.vendedoresGenericos().size.shouldBe(0)
    }
    it("coleccion con 1 vendedor") {
      centro.agregarVendedor(vendedor2)
      centro.vendedoresGenericos().size.shouldBe(1)
    }
    /*
    it("coleccion con 1 vendedor") {

      centro.vendedoresGenericos().shouldBe(List<Vendedor>())
      //queria que en should be sea igual a la lista con el vendedor2
    }
    */
  }

  describe("centro robusto") {
    val vendedor = VendedorFijo(sanIgnacio)
    val vendedor2 = VendedorFijo(sanIgnacio)
    val vendedor3 = VendedorFijo(sanIgnacio)
    val centro = CentroDistribucion(sanIgnacio)
    val certificacion = Certificacion(true,50)
    vendedor.agregarCertificacion(certificacion)
    vendedor2.agregarCertificacion(certificacion)
    centro.agregarVendedor(vendedor)
    centro.agregarVendedor(vendedor2)

    it("no es robusto") {
      centro.esRobusto().shouldBeFalse()
    }
    it("es robusto") {
      vendedor3.agregarCertificacion(certificacion)
      centro.agregarVendedor(vendedor3)
      centro.esRobusto().shouldBeTrue()
    }

  }

})

