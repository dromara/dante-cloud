package cn.herodotus.eurynome.integration.content.domain.tianyan;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 天眼查企业基本信息中的分类信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 9:43
 */
@ApiModel(description = "天眼查企业基本信息中的分类信息")
public class Industry {

	@ApiModelProperty(value = "国民经济行业分类门类")
	private String category;

	@ApiModelProperty(value = "国民经济行业分类大类")
	private String categoryBig;

	@ApiModelProperty(value = "国民经济行业分类中类")
	private String categoryMiddle;

	@ApiModelProperty(value = "国民经济行业分类小类")
	private String categorySmall;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryBig() {
		return categoryBig;
	}

	public void setCategoryBig(String categoryBig) {
		this.categoryBig = categoryBig;
	}

	public String getCategoryMiddle() {
		return categoryMiddle;
	}

	public void setCategoryMiddle(String categoryMiddle) {
		this.categoryMiddle = categoryMiddle;
	}

	public String getCategorySmall() {
		return categorySmall;
	}

	public void setCategorySmall(String categorySmall) {
		this.categorySmall = categorySmall;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("category", category)
				.add("categoryBig", categoryBig)
				.add("categoryMiddle", categoryMiddle)
				.add("categorySmall", categorySmall)
				.toString();
	}
}
