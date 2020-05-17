package b13.dao.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class Pager {
	
	public static Pageable of(int offset, int limit) {
		return PageRequest.of(offset, limit);
	}
}
