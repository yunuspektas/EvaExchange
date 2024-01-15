
* Spring Security Jwt Based Authentication kullanildi, metod bazli yetkilendirme kontrolu yapildi
	Authenticate edilen kullanicilardan gelen her requestin icine hangi kullanici oldugu bilgisinin anlasilmasi icin unique olan username bilgisi eklendi.
	Sistemde 2 farkli rol tanimlandi, Admin ve Customer :

					      
		    * Cutomer in yapabilecekleri	=	Sisteme login olma
					       			Stock Alis islemi
					       			Stock Satis islemi
					       			Kendine ait Butun Trade Bilgilerini getirme
	    
	        * Admin in yapabilecekleri 	=	Sisteme login olma
					       			Sisteme yeni Customer ekleme
					       			Sisteme yeni Stock Ekleme
		

* Response larin tamaminda DTO nesneleri kullanild ve DTO - POJO donusumleri icin manuel mapper siniflari yazildi

* Sistemde firlama ihtimali olan exceptionlar icin 2 farkli Custom Exception siniflari tanimlandi,  
	1.ResourceNotFoundException
	2.Conflict Exception
	
* Exception ve Success Mesajlari icin Ayri siniflar olusturularak hata ve basari mesajlari dinamik hale getirildi.

* Uygulama ilk basladiginda :
	1 tane Admin 
	5 tane Customer 
	3 tane Stock olusturuyor, her kullanici icin 1 er tane Alis islemi gerceklestirilmis sekilde sistem hazirdir.
	
* Database credentials bilgileri application.properties dosyasinda mevcuttur.



