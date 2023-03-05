package com.rosszhang.syncplayer.client.service.impl;

import com.rosszhang.syncplayer.client.service.VideoController;
import com.rosszhang.syncplayer.client.service.WebSimulate;
import com.rosszhang.syncplayer.client.utils.CommonUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Optional;

public class YoutubeController extends WebSimulate implements VideoController {

    private final String webSite = "https://www.youtube.com";
    private WebDriver driver;
    private WebDriverWait driverWait;
    private final String searchIconXpath = "//*[@id='primary-menu']/li[9]/a";
    private final String startButtonXpath = "//*[@id='movie_player']/div[33]/div[2]/div[1]/button";
    private final String cssButton = "button.ytp-play-button.ytp-button";
    private final String cssProgressBar = "div.ytp-progress-bar";
    private final String playButtonXpath = "//*[@id='vjsp']/div[4]/button[1]";
    private static final Logger log = LoggerFactory.getLogger(YoutubeController.class);
    private double videoDuration;

    public YoutubeController() {
        super();
        driver = getDriver();
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Override
    public void openVideoWebSite() {

    }

    @Override
    public void searchVideoByName(String videoName) {

    }

    @Override
    public void pauseVideoByUrl(String url) {

    }

    @Override
    public void openVideoByUrl(String url) {
        this.driver.get(url);
        this.driverWait.until(ExpectedConditions.urlToBe(url));
    }

    @Override
    public void playCurrentVideo() {
        //*[@id="movie_player"]/div[32]/div[2]/div[1]/button
//        #movie_player > div.ytp-chrome-bottom > div.ytp-chrome-controls > div.ytp-left-controls > button
        //*[@id="movie_player"]/div[31]/div[2]/div[1]/button
        if (!isStart()) {
            driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssButton))).click();
        }
    }

    private boolean isStart() {
        WebElement element = driver.findElement(By.cssSelector(cssButton));
        String state = element.getAttribute("data-title-no-tooltip");
        return "暂停".equals(state);
    }

    @Override
    public Integer listenProgress() {
        WebElement progressBar = driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssProgressBar)));
        String progressTime = progressBar.getAttribute("aria-valuetext");
        this.videoDuration = Integer.parseInt(progressBar.getAttribute("aria-valuemax"));
        String time = Optional.ofNullable(progressTime).orElse("");
        String[] splitTime = time.split("/");
        int seconds = CommonUtils.calculateYTTimeToSecond(splitTime[0]);
        log.info("current time {}s", seconds);
        return seconds;
    }

    @Override
    public void syncProgress(int curSeconds) {
        if (curSeconds == 0) {
            return;
        }
        boolean isStartFlag = isStart();
        //change video state from play to pause
        if (isStartFlag) {
            driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssButton))).click();
        }
        WebElement progressBar = driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssProgressBar)));
        Point location = progressBar.getLocation();
        log.info("location is {} {}", location.getX(), location.getY());
        Dimension outerSize = progressBar.getSize();
        log.info("size is {}", outerSize);
        double xOffset = outerSize.getWidth() / videoDuration * curSeconds - outerSize.getWidth() / 2.0;
        log.info("xOffset is {}", xOffset);
        new Actions(driver).moveToElement(progressBar, (int) xOffset, 0).click().perform();

        if (isStartFlag) {
            driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssButton))).click();
        }
    }
}
