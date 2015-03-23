package com.cf.fuer.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.bank.ChargeNumberUtils;
import com.cf.fuer.dao.IChargeDao;
import com.cf.fuer.exception.ObjectNotFoundException;
import com.cf.fuer.gc.MessageConstants;
import com.cf.fuer.model.Charge;
import com.cf.fuer.webapp.bean.SearchBean;

@Repository("chargeDao")
public class ChargeDaoImpl implements IChargeDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void search(SearchBean searchBean) {
		List<Charge> list = search(searchBean.getQuerySql(), searchBean.getParamMap());
		searchBean.setList(list);
		int totalCount = jdbcTemplate.queryForInt(searchBean.getTotalCountSql(), searchBean.getParamMap());
		searchBean.setFullListSize(totalCount);
	}

	private List<Charge> search(String sql, Map<String, Object> paramMap) {
		List<Charge> list = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<Charge>(Charge.class));
		return list;
	}

	@Override
	public List<Charge> fetchUnFinishedCharge(String serverCode) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql.append("SELECT * FROM charge WHERE status=" + CommonConstants.CHARGE_STATUS.PAY_SUCCESS + " OR status=" + CommonConstants.CHARGE_STATUS.CHARGE_FAIL);
		if (!MessageConstants.ALL_GAME_SERVER.equals(serverCode)) {// 单台服务器
			sql.append(" AND gameServer=:serverCode ");
			paramMap.put("serverCode", serverCode);
		}
		sql.append(" ORDER BY payDate DESC");// 付款日期降序
		return search(sql.toString(), paramMap);
	}

	@Override
	public Long createCharge(Charge charge) {
		final String sql = "insert into charge (orderId,chargeAmount,user,gameServer,payUser,status,createDate,goldMoney,presentGold,presentName,managerName) values(:orderId,:chargeAmount,:user,:gameServer,:payUser,:status,now(),:goldMoney,:presentGold,:presentName,:managerName)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		charge.setOrderId(ChargeNumberUtils.generateChargeNumber());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", charge.getOrderId());
		paramMap.put("chargeAmount", charge.getChargeAmount());
		paramMap.put("user", charge.getUser());
		paramMap.put("gameServer", charge.getGameServer());
		paramMap.put("payUser", charge.getPayUser());
		paramMap.put("status", CommonConstants.CHARGE_STATUS.UNPAY);// 订单为未支付
		paramMap.put("goldMoney", charge.getGoldMoney());
		paramMap.put("presentGold", charge.getPresentGold());
		paramMap.put("presentName", charge.getPresentName());
		paramMap.put("managerName", charge.getManagerName());
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public Charge get(Long id) {
		String sql = "select * from charge where id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		Charge charge = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<Charge>(Charge.class));
		if (charge == null || charge.getId() == null) {
			throw new ObjectNotFoundException();
		} else {
			return charge;
		}
	}

	/**
	 * 根据订单号获取充值订单
	 */
	@Override
	public Charge getByOrderId(Long orderId) {
		String sql = "select * from charge where orderId=:orderId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		Charge charge = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<Charge>(Charge.class));
		if (charge == null || charge.getId() == null) {
			throw new ObjectNotFoundException();
		} else {
			return charge;
		}
	}

	@Override
	public Charge getByDealId(String dealId) {
		try{
			String sql = "select * from charge where dealId=:dealId";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dealId", dealId);
			Charge charge = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<Charge>(Charge.class));
			return charge;
		}catch (Exception e) {
			return null;
		}
	}

	/**
	 * 服务器是否有充值活动
	 * 
	 * @param serverCode
	 *            服务器编码
	 * @return true 服务器有活动; false 服务器无充值活动
	 */
	@Override
	public boolean hasActivity(String serverCode) {
		try {
			String sql = "select count(*) from chargeactivity where serverCode=:serverCode AND startDate<=:chargeDate AND endDate>:chargeDate ";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("serverCode", serverCode);
			paramMap.put("chargeDate", new Date());
			// 能查询到说明有活动
			int count = jdbcTemplate.queryForInt(sql, paramMap);
			return count > 0;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 完成支付,返回更新的条数0(失败)或1(成功)
	 */
	@Override
	public int finishPay(Charge charge) {
		String sql = "UPDATE charge SET dealId=:dealId, dealTime=:dealTime, payAmount=:payAmount, payType=:payType, bankId=:bankId,payDate=now(), bankDealId=:bankDealId, errCode=:errCode, fee=:fee,status=:status WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dealId", charge.getDealId());
		paramMap.put("dealTime", charge.getDealTime());
		paramMap.put("payAmount", charge.getPayAmount());
		paramMap.put("payType", charge.getPayType());
		paramMap.put("bankId", charge.getBankId());
		paramMap.put("bankDealId", charge.getBankDealId());
		paramMap.put("errCode", charge.getErrCode());
		paramMap.put("fee", charge.getFee());
		paramMap.put("status", charge.getStatus());
		paramMap.put("id", charge.getId());
		int result = jdbcTemplate.update(sql, paramMap);
		
		// 同时更新充值次数和充值金额
		String updateSql = "UPDATE gamerole SET chargeCount=chargeCount+1, chargeAmount=chargeAmount+" + charge.getPayAmount() + " WHERE username=:username AND serverCode=:serverCode";
		Map<String, Object> updateParamMap = new HashMap<String, Object>();
		updateParamMap.put("username", charge.getUser());
		updateParamMap.put("serverCode", charge.getGameServer());
		jdbcTemplate.update(updateSql, updateParamMap);
		
		return result;
	}

	/**
	 * 完成充值到游戏服务器.(只有付款成功和充值失败的订单才可以完成为充值成功或失败)
	 * 
	 * @param orderId
	 *            充值编号
	 * @param status
	 *            状态,只能为充值成功或失败
	 * @return 更新的条数0(失败)或1(成功)
	 */
	@Override
	public int finishCharge(String orderId, int status) {
		// 只能改为充值成功或充值失败的状态
		if (status != CommonConstants.CHARGE_STATUS.CHARGE_SUCCESS && status != CommonConstants.CHARGE_STATUS.CHARGE_FAIL) {
			return 0;
		}
		// 只有付款成功或充值失败的状态才会更新
		String sql = "UPDATE charge SET chargeDate=now(), status=:status WHERE orderId=:orderId ";
		sql = sql + " AND (status=" + CommonConstants.CHARGE_STATUS.PAY_SUCCESS + " OR status=" + CommonConstants.CHARGE_STATUS.CHARGE_FAIL + ") ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		paramMap.put("orderId", orderId);
		return jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public Long getMaxOrderId() {
		String sql = "SELECT orderId FROM charge where id=(select max(id) from charge); ";
		Long orderId = jdbcTemplate.queryForLong(sql, new HashMap<String, Object>());
		return orderId;
	}

	@Override
	public void abandonedUnPayChargeOrder(Date abandonDate) {
		// 只有未付款且日期大于3天的会被作废
		String sql = "UPDATE charge SET status=:status WHERE status =" + CommonConstants.CHARGE_STATUS.UNPAY + " AND createDate<:abandonDate ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", CommonConstants.CHARGE_STATUS.ORDER_ABANDONED);
		paramMap.put("abandonDate", abandonDate);
		jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public int cancelChargeOrder(String orderId) {
		// 只有未付款的订单才能取消
		String sql = "UPDATE charge SET status=:status WHERE orderId=:orderId AND status =" + CommonConstants.CHARGE_STATUS.UNPAY;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", CommonConstants.CHARGE_STATUS.ORDER_CANCELLED);
		paramMap.put("orderId", orderId);
		return jdbcTemplate.update(sql, paramMap);
	}

	/**
	 * 创建第三方平台的充值订单
	 * 
	 * @param charge
	 * @return
	 */
	@Override
	public Long createPlatformCharge(Charge charge) {
		final String sql = "insert into charge (orderId,dealId,chargeAmount,payAmount,user,payUser,managerName,gameServer, createDate,payDate,chargeDate, goldMoney,presentGold,presentName,UserIP,status) values(:orderId,:dealId,:chargeAmount,:payAmount,:user,:payUser,:managerName,:gameServer, :createDate,:payDate,:chargeDate, :goldMoney,:presentGold,:presentName,:UserIP,:status);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", charge.getOrderId());
		paramMap.put("dealId", charge.getDealId());
		paramMap.put("chargeAmount", charge.getChargeAmount());
		paramMap.put("payAmount", charge.getPayAmount());
		paramMap.put("user", charge.getUser());
		paramMap.put("payUser", charge.getPayUser());
		paramMap.put("managerName", charge.getManagerName());
		paramMap.put("gameServer", charge.getGameServer());
		paramMap.put("createDate", charge.getCreateDate());
		paramMap.put("payDate", charge.getPayDate());
		paramMap.put("chargeDate", charge.getChargeDate());
		paramMap.put("goldMoney", charge.getGoldMoney());
		paramMap.put("presentGold", charge.getPresentGold());
		paramMap.put("presentName", charge.getPresentName());
		paramMap.put("presentName", charge.getPresentName());
		paramMap.put("UserIP", charge.getUserIP());
		paramMap.put("status", CommonConstants.CHARGE_STATUS.CHARGE_SUCCESS);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);

		// 同时更新充值次数和充值金额
		String updateSql = "UPDATE gamerole SET chargeCount=chargeCount+1, chargeAmount=chargeAmount+" + charge.getPayAmount() + " WHERE username=:username AND serverCode=:serverCode";
		Map<String, Object> updateParamMap = new HashMap<String, Object>();
		updateParamMap.put("username", charge.getUser());
		updateParamMap.put("serverCode", charge.getGameServer());
		jdbcTemplate.update(updateSql, updateParamMap);

		return keyHolder.getKey().longValue();
	}

}
