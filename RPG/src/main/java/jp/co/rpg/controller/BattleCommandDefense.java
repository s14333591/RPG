package jp.co.rpg.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.rpg.dao.EnemyDao;
import jp.co.rpg.entity.BattleInfo;
import jp.co.rpg.entity.Enemy;
import jp.co.rpg.entity.User;

@RestController
@ResponseBody
public class BattleCommandDefense{
	@Autowired
	HttpSession session;
	@Autowired
	private EnemyDao enemyDao;

	//ぼうぎょ
	@RequestMapping("/defense")
	public BattleInfo defense() {

		//初期情報取得
		User user = (User) session.getAttribute("user");
		Enemy enemy = (Enemy) session.getAttribute("enemy");
		BattleInfo bi = new BattleInfo();
		Integer userHp = user.getHp();

		bi.setContext(user.getName() + "はぼうぎょしている");
		bi.setContext(enemy.getName() + "の攻撃");
		bi.setContext(user.getName() + "は" + enemy.getPower() / 2 + "のダメージを受けた");
		userHp -= enemy.getPower() / 2;

		if(userHp > 0) {
			//バトル継続
			user.setHp(userHp);
			bi.setUserHp(userHp);
		}else {
			//バトル不能
			bi.setIsContinue(false);
			bi.setUserHp(0);
			user.setHp(1);
			user.setGold(user.getGold() / 2 );
		}

		session.setAttribute("user", user);

		return bi;
	}
}