# ApartmanYonetimSistemi
Bu projede bir Apartman Yönetim Sistemi yapılmıştır. Kod java dilinde yazılmış olup 27 adet dosyadan oluşmaktadır. Veritabanıyla uyumlu bir şekilde çalışan kodumuz State, Observer, Singleton, AbstractFactory, Design tasarım desenleriyle uyumlu yapılmaya çalışılmıştır.
CRUD işlemlerini desteklemektedir. Jframe ile yapılmış rol bazlı bir arayüzü vardır.
# Roller ve Özellikleri
Admin: Admin bizim yöneticimizdir. Kullanıcı ekle, güncelle ve silme özellikleriyle diğer kullanıcıları yönetir. Apartmanların durumlarını değiştirebilir örneğin boş, satılmış vb. 
Dairelerin güncel durumlarını görüntüleyebilir. Tenant ve Owner kullanıcılarına bildirim gönderebilir.

Tenant: Dairelerde oturan kiracılardır. Dairesini görüntüleyebilir ve oturduğu daireden çıkabilir. Adminden gelen bildirimleri görebilir okundu olarak işaretleyebilir.

Owner: Dairelerin sahibidir. Tenant gibi dairesini görüntüleyebilir ve dairesini satabilir. Adminden gelen bildirimleri görebilir okundu olarak işaretleyebilir.

Kodumuz AbstractFactory tasarım desenine uygun olduğu için istenirse bu örnekler çoğaltılabilir.
# Sınıflar
Admin: User sınıfını miras alır ve içerisinde kendi yapıcı metodunu barındırır. Eğer istenirse konsolda çalıştırmak için gerekli kodlar eklenebilir.

AdminFactory: AbstractFactory tasarım desenine uygun olması için eklenmiştir. Admin rolünden kullanıcı eklemek için kullanılır.

AdminFrame: Admin özelliklerini barından Jframe sınıfını miras alan bir sınıftır. GUI desteklidir ve içerisinde Adminin işlevlerini taşıyan metodlar vardır.

AdminNotificationService: Adminin diğer rollere bildirim yollamasını sağlayan yapıcı metodları barındırır.

Apartment: Apartmanların özelliklerini barındıran yapıcı metoda sahiptir aynı zamanda Kapsülleme özelliğini destekler.

ApartmentRepository: Apartmanlarla ilgili değişiklikler yapılmasını veya görüntülenmesini sağlayan metodlar barındırmaktadır.

ApartmentState: Apartmanların durumunu görüntülenmesini ve değiştirilmesini sağlayan Interface.

DatabaseConnection: Veritabanı bağlantısının yapıldı sınıftır. Singleton tasarım desenine uygun tasarlanmıştır.

Empty/Rented/Sold State: Bu sınıflar apartmanlarda durum güncellemesi yapılması için vardır. Örnekler çoğaltılabilir.

NotificationManager: Abonelik sistemlerinin yönetildiği ve Veritabanına bildirimlerin kaydedildiği sınıftır. Abonelik sistemi pasif durumdadır yani her kullanıcı abone sayılmaktadır fakat istenirse bu sınıftaki metod üzerinden aboneler eklenebilir.

NotificationRepository: Owner ve Tenant kullanıcılarının bildirimlerini görüntüleyebildiği ve okundu olarak işaretleyebildiği sınıftır.

Observer: Observer tasarım desenine uygun olması için tanımlanmış Interface.

Owner: User sınıfını miras alır ve içerisinde kendi yapıcı metodunu barındırır. Eğer istenirse konsolda çalıştırmak için gerekli kodlar eklenebilir.

OwnerFactory:  AbstractFactory tasarım desenine uygun olması için eklenmiştir. Owner rolünden kullanıcı eklemek için kullanılır.

OwnerFrame: Owner özelliklerini barından Jframe sınıfını miras alan bir sınıftır. GUI desteklidir ve içerisinde Owner işlevlerini taşıyan metodlar vardır.

Subject: Abonelik sisteminin Interface kısmıdır.

Tenant:  User sınıfını miras alır ve içerisinde kendi yapıcı metodunu barındırır. Eğer istenirse konsolda çalıştırmak için gerekli kodlar eklenebilir.

TenantFactory: AbstractFactory tasarım desenine uygun olması için eklenmiştir. Tenant rolünden kullanıcı eklemek için kullanılır.

TenantFrame: Tenant özelliklerini barından Jframe sınıfını miras alan bir sınıftır. GUI desteklidir ve içerisinde Tenant işlevlerini taşıyan metodlar vardır.

User: Admin, Tenant, Owner sınıflarının Parent soyut sınıfıdır.

UserFactory: AdminFactory, OwnerFactory, TenantFactory sınıflarının Parent soyut sınıfıdır. Genel işlevi miras alan sınıfların kendi rolünü türetmesidir.

UserNotification: Owner ve Tenant kullanıcılarının bildirim aldıkları ve bildirimleri okundu olarak işaretledikleri yerdir.

UserRepository: Kullanıcılarla ilgili tüm işlemleri barındıran sınıftır. Buradaki metodları kullanarak Admin, Owner, Tenant kullanıcıları kendi rollerine uygun işlevleri yerine getirirler
