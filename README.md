---Cinema Ticket Reservation System---

Nesne Yönelimli Programlama (OOP) dersi dönem projesi olarak yaptığım sinema otomasyonu uygulaması.

### Proje Ne Yapıyor?
Bu uygulama ile sinema bileti alma işlemlerini simüle etmeye çalıştım.
* Kullanıcılar sisteme **Kayıt Olup** veya **Giriş Yapıp** işlem yapabiliyor.
* Vizyondaki filmleri ve seans saatlerini görebiliyoruz.
* Sinema salonundaki koltukları görüntüleyip seçim yapabiliyoruz.
* 2D ve 3D filmlere göre bilet fiyatı değişiyor.
* Yaptığımız işlemler **SQLite** veritabanına kaydediliyor, uygulama kapansa bile veriler silinmiyor.

### Kullandığım Teknolojiler
* **Dil:** Java
* **Veritabanı:** SQLite
* **IDE:** IntelliJ IDEA

### Projede Kullandığım OOP Kavramları
Projeyi yaparken derste gördüğümüz şu konuları kullandım:
* **Inheritance (Kalıtım):** Filmleri tek bir sınıftan türettim.
* **Encapsulation (Kapsülleme):** Verileri private yapıp getter/setter kullandım.
* **Polymorphism:** Fiyat hesaplamasını film türüne göre değiştirdim.
* **Interface:** Rezervasyon işlemleri için ortak yapı kurdum.

