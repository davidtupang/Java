package del.group10.java_ee.jpa;




import org.springframework.data.mongodb.repository.MongoRepository;




public interface StoreRepository extends MongoRepository<Store ,String> {
	
	
	
}
