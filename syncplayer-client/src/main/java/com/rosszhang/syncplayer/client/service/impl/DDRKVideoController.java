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

public class DDRKVideoController extends WebSimulate implements VideoController{

    private final String webSite = "https://ddys.art";
    private WebDriver driver;
    private WebDriverWait driverWait;
    private final String searchIconXpath = "//*[@id='primary-menu']/li[9]/a";
    private final String startButtonClassName = "vjs-big-play-button";
    private final String progressBarXpath = "//*[@id='vjsp']/div[4]/div[5]/div";
    private final String playButtonXpath = "//*[@id='vjsp']/div[4]/button[1]";
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
//        "//*[@id='primary-menu']/li[9]/a";
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchIconXpath))).click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("s"))).sendKeys(videoName + Keys.ENTER);
//        driver.findElement(By.name("s")).sendKeys(videoName + Keys.ENTER);
        driver.findElement(By.partialLinkText(videoName)).click();
    }

    @Override
    public void pauseVideoByUrl(String url) {

    }

    @Override
    public void openVideoByUrl(String url) {
        this.driver.get(url);
    }

    @Override
    public void playCurrentVideo() {
        driverWait.until(ExpectedConditions.elementToBeClickable(By.className(startButtonClassName)))
                .click();

    }

    @Override
    public Integer listenProgress() {
        WebElement progressBar = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(progressBarXpath)));
        String progressTime = progressBar.getAttribute("aria-valuetext");
        String time = Optional.ofNullable(progressTime).orElse("");
        String[] splitTime = time.split("/");
        int seconds = CommonUtils.calculateToSecond(splitTime[0]);
        log.info("current seconds {}", seconds);
        return seconds;
    }

    @Override
    public void syncProgress(int curSeconds) {
        if (curSeconds == 0) {
            return;
        }
        WebElement element = driver.findElement(By.xpath(playButtonXpath));
        // current video is playing
        boolean isNeedPlay = false;
        if (!"播放".equals(element.getAttribute("title"))) {
            isNeedPlay = true;
            new Actions(driver).moveToElement(element).click().perform();
        }


        WebElement progressBar = driver.findElement(By.xpath(progressBarXpath));
        Point location = progressBar.getLocation();
        log.info("location is {} {}", location.getX(), location.getY());
        Dimension size = progressBar.getSize();
        log.info("size is {}", size);
        new Actions(driver).moveToElement(progressBar, size.getWidth() / 2, 0).click().perform();

    }


}
