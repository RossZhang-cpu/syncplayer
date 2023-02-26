package com.rosszhang.syncplayer.client.service.impl;

import com.rosszhang.syncplayer.client.service.VideoController;
import com.rosszhang.syncplayer.client.service.WebSimulate;
import com.rosszhang.syncplayer.client.utils.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Optional;

public class DDRKVideoController extends WebSimulate implements VideoController{

    private final String webSite = "https://ddys.art";
    private WebDriver driver;
    private WebDriverWait driverWait;
    private final String searchBarXpath = "//*[@id='primary-menu']/li[9]/a";
    private final String playButtonClassName = "vjs-big-play-button";
    private final String progressBarXpath = "//*[@id='vjsp']/div[4]/div[5]/div";
    private static final Logger log = LoggerFactory.getLogger(DDRKVideoController.class);


    public DDRKVideoController() {
        super();
        driver = getDriver();
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Override
    public void openVideoWebSite() {
        this.driver.get(webSite);
    }

    @Override
    public void searchVideoByName(String videoName) {
        //TODO: ensure in the website where can use to search
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchBarXpath)))
                .click();
        driver.findElement(By.name("s")).sendKeys(videoName + Keys.ENTER);
        driver.findElement(By.partialLinkText(videoName)).click();
    }

    @Override
    public void pauseVideoByUrl(String url) {

    }

    @Override
    public void playVideoByUrl(String url) {

    }

    @Override
    public void playCurrentVideo() {
        driverWait.until(ExpectedConditions.elementToBeClickable(By.className(playButtonClassName)))
                .click();

    }

    @Override
    public Integer listenProgress() {
        WebElement progressBar = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='vjsp']/div[4]/div[5]/div")));
        String progressTime = progressBar.getAttribute("aria-valuetext");
        String time = Optional.ofNullable(progressTime).orElse("");
        String[] splitTime = time.split("/");
        int seconds = CommonUtils.caculateToSecond(splitTime[0]);
        log.info("current seconds {}", seconds);
        return seconds;
    }

    @Override
    public void syncProgress(int curSeconds) {

    }
}
