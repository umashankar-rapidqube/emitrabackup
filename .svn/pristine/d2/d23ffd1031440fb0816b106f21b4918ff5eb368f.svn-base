package service;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.PUCDao;
import model.PUCModel;

@Service("PUCService")
public class PUCServiceImpl implements PUCService {

	static final Logger logger = Logger.getLogger(PUCServiceImpl.class);

	@Autowired
	PUCDao pucDao;

	@Override
	public JSONObject insertData(PUCModel pucModel) {
		// TODO Auto-generated method stub
		return pucDao.insertData(pucModel);
	}
}