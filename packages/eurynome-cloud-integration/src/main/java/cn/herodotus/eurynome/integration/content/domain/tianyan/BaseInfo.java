package cn.herodotus.eurynome.integration.content.domain.tianyan;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigInteger;

/**
 * <p>Description: 天眼查企业基本信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 17:25
 */
@ApiModel(description = "天眼查企业基本信息")
public class BaseInfo {

	@ApiModelProperty(value = "人员规模", notes = "varchar(200)")
	private String staffNumRange;

	@ApiModelProperty(value = "经营开始时间", notes = "毫秒数")
	private BigInteger fromTime;

	@ApiModelProperty(value = "法人类型", notes = "1 人 2 公司")
	private Integer type;

	@ApiModelProperty(value = "股票名", notes = "varchar(20)")
	private String bondName;

	@ApiModelProperty(value = "企业id")
	private BigInteger id;

	@ApiModelProperty(value = "是否是小微企业", notes = "0不是 1是")
	private Integer isMicroEnt;

	@ApiModelProperty(value = "股票曾用名", notes = "varchar(20)")
	private String usedBondName;

	@ApiModelProperty(value = "注册号", notes = "varchar(31)")
	private String regNumber;

	@ApiModelProperty(value = "企业评分", notes = "万分制")
	private Integer percentileScore;

	@ApiModelProperty(value = "注册资本", notes = "varchar(50)")
	private String regCapital;

	@ApiModelProperty(value = "企业名", notes = "varchar(255)")
	private String name;

	@ApiModelProperty(value = "登记机关", notes = "varchar(255)")
	private String regInstitute;

	@ApiModelProperty(value = "注册地址", notes = "varchar(255)")
	private String regLocation;

	@ApiModelProperty(value = "行业", notes = "varchar(255)")
	private String industry;

	@ApiModelProperty(value = "核准时间", notes = "毫秒数")
	private BigInteger approvedTime;

	@ApiModelProperty(value = "参保人数")
	private Integer socialStaffNum;

	@ApiModelProperty(value = "企业标签", notes = "varchar(255)")
	private String tags;

	@ApiModelProperty(value = "纳税人识别号", notes = "varchar(255)")
	private String taxNumber;

	@ApiModelProperty(value = "经营范围", notes = "varchar(4091)")
	private String businessScope;

	@ApiModelProperty(value = "英文名", notes = "varchar(255)")
	private String property3;

	@ApiModelProperty(value = "简称", notes = "varchar(255)")
	private String alias;

	@ApiModelProperty(value = "组织机构代码", notes = "varchar(31)\t")
	private String orgNumber;

	@ApiModelProperty(value = "企业状态", notes = "varchar(31)")
	private String regStatus;

	@ApiModelProperty(value = "成立日期", notes = "毫秒数")
	private BigInteger estiblishTime;

	@ApiModelProperty(value = "更新时间", notes = "毫秒数")
	private BigInteger updateTimes;

	@ApiModelProperty(value = "股票类型", notes = "varchar(31)")
	private String bondType;

	@ApiModelProperty(value = "法人", notes = "varchar(120)")
	private String legalPersonName;

	@ApiModelProperty(value = "经营结束时间", notes = "毫秒数")
	private Integer toTime;

	@ApiModelProperty(value = "实收注册资金", notes = "varchar(50)")
	private String actualCapital;

	@ApiModelProperty(value = "企业类型", notes = "varchar(127)")
	private String companyOrgType;

	@ApiModelProperty(value = "省份简称", notes = "varchar(31)")
	private String base;

	@ApiModelProperty(value = "统一社会信用代码", notes = "varchar(255)")
	private String creditCode;

	@ApiModelProperty(value = "曾用名", notes = "varchar(255)")
	private String historyNames;

	@ApiModelProperty(value = "曾用名")
	private String[] historyNameList;

	@ApiModelProperty(value = "股票号", notes = "varchar(20)")
	private String bondNum;

	@ApiModelProperty(value = "注册资本币种 人民币 美元 欧元 等", notes = "varchar(10)\t")
	private String regCapitalCurrency;

	@ApiModelProperty(value = "实收注册资本币种 人民币 美元 欧元 等", notes = "varchar(10)")
	private String actualCapitalCurrency;

	@ApiModelProperty(value = "吊销日期", notes = "毫秒数")
	private BigInteger revokeDate;

	@ApiModelProperty(value = "吊销原因", notes = "varchar(500)")
	private String revokeReason;

	@ApiModelProperty(value = "注销日期", notes = "毫秒数")
	private BigInteger cancelDate;

	@ApiModelProperty(value = "注销原因", notes = "varchar(500)")
	private String cancelReason;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "区")
	private String district;

	@ApiModelProperty(value = "国民经济行业分类")
	private Industry industryAll;

	public String getStaffNumRange() {
		return staffNumRange;
	}

	public void setStaffNumRange(String staffNumRange) {
		this.staffNumRange = staffNumRange;
	}

	public BigInteger getFromTime() {
		return fromTime;
	}

	public void setFromTime(BigInteger fromTime) {
		this.fromTime = fromTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getIsMicroEnt() {
		return isMicroEnt;
	}

	public void setIsMicroEnt(Integer isMicroEnt) {
		this.isMicroEnt = isMicroEnt;
	}

	public String getUsedBondName() {
		return usedBondName;
	}

	public void setUsedBondName(String usedBondName) {
		this.usedBondName = usedBondName;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Integer getPercentileScore() {
		return percentileScore;
	}

	public void setPercentileScore(Integer percentileScore) {
		this.percentileScore = percentileScore;
	}

	public String getRegCapital() {
		return regCapital;
	}

	public void setRegCapital(String regCapital) {
		this.regCapital = regCapital;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegInstitute() {
		return regInstitute;
	}

	public void setRegInstitute(String regInstitute) {
		this.regInstitute = regInstitute;
	}

	public String getRegLocation() {
		return regLocation;
	}

	public void setRegLocation(String regLocation) {
		this.regLocation = regLocation;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public BigInteger getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(BigInteger approvedTime) {
		this.approvedTime = approvedTime;
	}

	public Integer getSocialStaffNum() {
		return socialStaffNum;
	}

	public void setSocialStaffNum(Integer socialStaffNum) {
		this.socialStaffNum = socialStaffNum;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getProperty3() {
		return property3;
	}

	public void setProperty3(String property3) {
		this.property3 = property3;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public BigInteger getEstiblishTime() {
		return estiblishTime;
	}

	public void setEstiblishTime(BigInteger estiblishTime) {
		this.estiblishTime = estiblishTime;
	}

	public BigInteger getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(BigInteger updateTimes) {
		this.updateTimes = updateTimes;
	}

	public String getBondType() {
		return bondType;
	}

	public void setBondType(String bondType) {
		this.bondType = bondType;
	}

	public String getLegalPersonName() {
		return legalPersonName;
	}

	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}

	public Integer getToTime() {
		return toTime;
	}

	public void setToTime(Integer toTime) {
		this.toTime = toTime;
	}

	public String getActualCapital() {
		return actualCapital;
	}

	public void setActualCapital(String actualCapital) {
		this.actualCapital = actualCapital;
	}

	public String getCompanyOrgType() {
		return companyOrgType;
	}

	public void setCompanyOrgType(String companyOrgType) {
		this.companyOrgType = companyOrgType;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getHistoryNames() {
		return historyNames;
	}

	public void setHistoryNames(String historyNames) {
		this.historyNames = historyNames;
	}

	public String[] getHistoryNameList() {
		return historyNameList;
	}

	public void setHistoryNameList(String[] historyNameList) {
		this.historyNameList = historyNameList;
	}

	public String getBondNum() {
		return bondNum;
	}

	public void setBondNum(String bondNum) {
		this.bondNum = bondNum;
	}

	public String getRegCapitalCurrency() {
		return regCapitalCurrency;
	}

	public void setRegCapitalCurrency(String regCapitalCurrency) {
		this.regCapitalCurrency = regCapitalCurrency;
	}

	public String getActualCapitalCurrency() {
		return actualCapitalCurrency;
	}

	public void setActualCapitalCurrency(String actualCapitalCurrency) {
		this.actualCapitalCurrency = actualCapitalCurrency;
	}

	public BigInteger getRevokeDate() {
		return revokeDate;
	}

	public void setRevokeDate(BigInteger revokeDate) {
		this.revokeDate = revokeDate;
	}

	public String getRevokeReason() {
		return revokeReason;
	}

	public void setRevokeReason(String revokeReason) {
		this.revokeReason = revokeReason;
	}

	public BigInteger getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(BigInteger cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Industry getIndustryAll() {
		return industryAll;
	}

	public void setIndustryAll(Industry industryAll) {
		this.industryAll = industryAll;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("staffNumRange", staffNumRange)
				.add("fromTime", fromTime)
				.add("type", type)
				.add("bondName", bondName)
				.add("id", id)
				.add("isMicroEnt", isMicroEnt)
				.add("usedBondName", usedBondName)
				.add("regNumber", regNumber)
				.add("percentileScore", percentileScore)
				.add("regCapital", regCapital)
				.add("name", name)
				.add("regInstitute", regInstitute)
				.add("regLocation", regLocation)
				.add("industry", industry)
				.add("approvedTime", approvedTime)
				.add("socialStaffNum", socialStaffNum)
				.add("tags", tags)
				.add("taxNumber", taxNumber)
				.add("businessScope", businessScope)
				.add("property3", property3)
				.add("alias", alias)
				.add("orgNumber", orgNumber)
				.add("regStatus", regStatus)
				.add("estiblishTime", estiblishTime)
				.add("updateTimes", updateTimes)
				.add("bondType", bondType)
				.add("legalPersonName", legalPersonName)
				.add("toTime", toTime)
				.add("actualCapital", actualCapital)
				.add("companyOrgType", companyOrgType)
				.add("base", base)
				.add("creditCode", creditCode)
				.add("historyNames", historyNames)
				.add("historyNameList", historyNameList)
				.add("bondNum", bondNum)
				.add("regCapitalCurrency", regCapitalCurrency)
				.add("actualCapitalCurrency", actualCapitalCurrency)
				.add("revokeDate", revokeDate)
				.add("revokeReason", revokeReason)
				.add("cancelDate", cancelDate)
				.add("cancelReason", cancelReason)
				.add("city", city)
				.add("district", district)
				.add("industryAll", industryAll)
				.toString();
	}
}
