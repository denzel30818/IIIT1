package tw.t1.service;

import java.util.List;

import tw.t1.entity.WishList;

public interface WishListService {

	public List<WishList> findAll();

	public WishList findById(int Id);

	public void save(WishList wishList);

	public void deleteById(int Id);

	public List<WishList> findByUser_id(int id);

	public List<WishList> findByUser_idAndP_id(int uId, int pId);
	
	public void deleteByUser_idAndP_id(int uId,int pId);
	
	public void deleteByP_id(int pId);
	
	public List<WishList> findByP_id(int pId);
}
