package tw.t1.generator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class generator implements IdentifierGenerator {


	@Transactional
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		String preId=getPreID(session);
		String newId;
		LocalDateTime dt = LocalDateTime.now();
		
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyyMMdd");  
		
		String now=dt.format(myFormat);	
		
		if(preId!=null) {
			String predate=preId.substring(0, 8);
			String Snum= preId.substring(8, 11);

			if(now.equals(predate)) {
				int newNum=Integer.parseInt(Snum.trim())+1;
				int length=String.valueOf(newNum).length();
				String newSnum="0".repeat(3-length)+String.valueOf(newNum);
				newId=now+newSnum;
			}else {
				newId=now+"001";			
			}
		}else {
			newId = now+"001";
		}
		
	
		System.out.println(Long.parseLong(newId));
		return Long.parseLong(newId);
		
	}
	
	
	public String getPreID(SharedSessionContractImplementor session) {
		
		String preId=null;

		 
		Object query = session.createSQLQuery("SELECT MAX(id) FROM orders").uniqueResult();
		
		if(query!=null) {
			preId= query.toString();	
		}
		
		
		System.out.println(preId);
		
		return preId;

	}
}
