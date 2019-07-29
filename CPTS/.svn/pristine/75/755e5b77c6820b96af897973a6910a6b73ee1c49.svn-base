package com.dchip.cloudparking.web.model.vo;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.dchip.cloudparking.web.model.po.Card;
import com.dchip.cloudparking.web.model.po.CardLicensePlate;

public class CardVo extends Card {
	
	private static final long serialVersionUID = 1682733569367867190L;

	private int lpId;
	private int cardId;
	private int isMain;
	private int moreCarType;
	private String licensePlate;
	private String expiryTimeString;
	private String secondLicensePlate;
	private String thirdLicensePlate;


	public static Card getCardByVo(CardVo vo) {
		Card card = new Card();
		card.setId(vo.getCardId());
		card.setParkingId(vo.getParkingId());
		card.setCardCode(vo.getCardCode());
		card.setCarOwnerName(vo.getCarOwnerName());
		card.setExpiryTime(vo.getExpiryTime());
		card.setMax(vo.getMax());
		card.setType(vo.getType());
		card.setRemark(vo.getRemark());
		card.setPhone(vo.getPhone());
		card.setAddress(vo.getAddress());
		card.setIsFixedSpace(vo.getIsFixedSpace());
		return card;
	}

	public static CardLicensePlate getCardLicensePlateByVo(CardVo vo ,String secondLicensePlate, String thirdLicensePlate) {
		CardLicensePlate plate = new CardLicensePlate();
		ArrayList<String> moreLicensePlate = new ArrayList<String>();
		if (secondLicensePlate != "") {
			moreLicensePlate.add(secondLicensePlate);
		}
		if (thirdLicensePlate != "") {
			moreLicensePlate.add(thirdLicensePlate);
		}
		plate.setCardId(vo.getCardId());
		plate.setIsMain(vo.getIsMain());
		plate.setLisencePlate(vo.getLicensePlate());
		plate.setMoreCarType(vo.getMoreCarType());
		if (secondLicensePlate != "" || thirdLicensePlate != "") {
			plate.setMoreCarLisencePlate(StringUtils.strip(moreLicensePlate.toString(),"[]"));
		}
		
		return plate;
	}


	public String getExpiryTimeString() {
		return expiryTimeString;
	}

	public void setExpiryTimeString(String expiryTimeString) {
		this.expiryTimeString = expiryTimeString;
	}

	public int getLpId() {
		return lpId;
	}

	public void setLpId(int lpId) {
		this.lpId = lpId;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public int getMoreCarType() {
		return moreCarType;
	}

	public void setMoreCarType(int moreCarType) {
		this.moreCarType = moreCarType;
	}

	public String getSecondLicensePlate() {
		return secondLicensePlate;
	}

	public void setSecondLicensePlate(String secondLicensePlate) {
		this.secondLicensePlate = secondLicensePlate;
	}

	public String getThirdLicensePlate() {
		return thirdLicensePlate;
	}

	public void setThirdLicensePlate(String thirdLicensePlate) {
		this.thirdLicensePlate = thirdLicensePlate;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getIsMain() {
		return isMain;
	}

	public void setIsMain(int isMain) {
		this.isMain = isMain;
	}

	public void setIsMain(String isMain) {
		if(isMain.trim().equals("是")){
			this.setIsMain(1);
		}else {
			this.setIsMain(0);
		}
	}

	public void setIsFixedSpace(String isFixedSpace) {
		if(isFixedSpace.trim().equals("是")){
			this.setIsFixedSpace(1);
		}else {
			this.setIsFixedSpace(0);
		}
	}

	public void setType(String type) {
		if(type.equals("月卡")){
			this.setType(1);
		}else if(type.equals("季卡")){
			this.setType(2);
		}else if(type.equals("年卡")){
			this.setType(3);
		}
	}
	@Override
	public String toString() {
		return "CardVo{" +
				"expiryTimeString='" + expiryTimeString + '\'' +
				", lpId=" + lpId +
				", cardId=" + cardId +
				", licensePlate='" + licensePlate + '\'' +
				", isMain=" + isMain +
				'}'+super.toString();
	}

}
