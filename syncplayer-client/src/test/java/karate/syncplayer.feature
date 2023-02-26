Feature: browser automation 1

  Background:
    * configure driver = { type: 'chrome', showDriverLog: true }
  # * configure driverTarget = { docker: 'justinribeiro/chrome-headless', showDriverLog: true }
  # * configure driverTarget = { docker: 'ptrthomas/karate-chrome', showDriverLog: true }
  # * configure driver = { type: 'chromedriver', showDriverLog: true }
  # * configure driver = { type: 'geckodriver', showDriverLog: true }
  # * configure driver = { type: 'safaridriver', showDriverLog: true }
  # * configure driver = { type: 'iedriver', showDriverLog: true, httpConfig: { readTimeout: 120000 } }

  Scenario: try to login to github
  and then do a google search

#    Given driver 'https://github.com/login'
#    And input('#login_field', 'dummy')
#    And input('#password', 'world')
#    When submit().click("input[name=commit]")
#    Then match html('.flash-error') contains 'Incorrect username or password.'

    Given driver 'https://google.com'
    And input("input[name=q]", 'karate dsl')
    When submit().click("input[name=btnI]")
    Then waitForUrl('https://github.com/intuit/karate')

  Scenario: watch video on ddys.tv
    Given driver 'https://ddys.art'
    * def sleep = function(millis){ java.lang.Thread.sleep(millis) }
    And waitForUrl(driver.url)
    And waitForEnabled("//*[@id='primary-menu']/li[9]/a").click()
    And input("input[name=s]", '初恋')
    * print "input movie name"
    When submit().click("//input[@value='搜索']")
    * print "searching"

    * waitUntil("document.readyState == 'complete'")
    * print "ready"
    * sleep(3000)
    And waitForEnabled('{^a}初恋').retry().click()
#    And waitForEnabled('{^a}初恋').click()
#    And leftOf('{a}').click()
#    * print 'click the first matched item'
#    * def sleep = function(millis){ java.lang.Thread.sleep(millis) }
#    * sleep(3000)
    And waitForUrl(driver.url)
    * def sleep = function(millis){ java.lang.Thread.sleep(millis) }
    * waitUntil("document.readyState == 'complete'")
    And mouse().move("button[title='播放视频']").click()

    And focus("//*[@id='vjsp']/div[4]/div[5]/div")
    * sleep(3000)
    And waitForUrl(driver.url)
    * print "log time..."
    *  def progress = attribute("//*[@id='vjsp']/div[4]/div[5]/div", "aria-valuetext")
    * print progress
    * print "log time end..."
    * def spiltCur = function(time){return time.toString().split('/')[0]}
    * def spiltDuration = function(time){return time.toString().split('/')[1]}
    * def currentTime = spiltCur(progress)
    * def duration = spiltDuration(progress)
    * print currentTime
    * print duration

  Scenario: click play and listen
    Given driver 'https://ddys.art/first-love/'
    And waitForUrl(driver.url)
    * def sleep = function(millis){ java.lang.Thread.sleep(millis) }
    * waitUntil("document.readyState == 'complete'")
    And mouse().move("button[title='播放视频']").click()

    And focus("//*[@id='vjsp']/div[4]/div[5]/div")
    * sleep(3000)
    And waitForUrl(driver.url)
    ## connect
    * def sleep = function(millis) { java.lang.Thread.sleep(millis) }
    * def diff = function(handler) { while(handler.getDiff() == null){java.lang.Thread.sleep(50)} }
    * def path = '/app/sync/1234'
    * def subscribeUrl = '/topic/synctime/1234'
    * def serverUrl = 'ws://localhost:9001/ws'
    * def javaClient = Java.type('com.rosszhang.syncplayer.client.service.WebSocketClientFacade')
    * def SessionHandler = Java.type('com.rosszhang.syncplayer.client.config.SyncTimeSessionHandler')
    * def handler = new SessionHandler()
    * def wsClient = new javaClient(serverUrl, handler)
    * wsClient.subscribe(subscribeUrl)
    ## every five seconds
    * sleep(5000)
    * def progress = attribute("//*[@id='vjsp']/div[4]/div[5]/div", "aria-valuetext")
    * print progress
    * print "log time end..."
    * def spiltCur = function(time){return time.toString().split('/')[0]}
    * def spiltDuration = function(time){return time.toString().split('/')[1]}
    * def currentTime = spiltCur(progress)
    * def duration = spiltDuration(progress)
    * print currentTime
#    * print duration
#    * def clientPlayer = Java.type('com.rosszhang.syncplayer.client.service.ClientPlayer.java')
    * wsClient.send(path, currentTime)
    * def diff = handler.listenResult()
    * print ("diff is :" + diff)
    ## send currentTime method(currentTime)
    ## do sync operation get syncTime




  Scenario: send and listening Websocket
    * def sleep = function(millis) { java.lang.Thread.sleep(millis) }
    * def diff = function(handler) { while(handler.getDiff() == null){java.lang.Thread.sleep(50)} }
    * def path = '/app/sync/1234'
    * def subscribeUrl = '/topic/synctime/1234'
    * def serverUrl = 'ws://localhost:9001/ws'
    * def javaClient = Java.type('com.rosszhang.syncplayer.client.service.WebSocketClientFacade')
    * def SessionHandler = Java.type('com.rosszhang.syncplayer.client.config.SyncTimeSessionHandler')
    * def handler = new SessionHandler()
    * def wsClient = new javaClient(serverUrl, handler)
    * wsClient.subscribe(subscribeUrl)
    * wsClient.send(path, '1')
    * sleep(80)
    * def diff = handler.listenResult()
    * print ("diff is :" + diff)




#    client side:
#      loop 3s
#
#        use websocket send currentTime to server
#
#
#
#    server side:
#      listening message
#      if new message
#          get client curTime
#         if >= 3s differences
#            minTime = min(CurrentTime1, CurrentTime2);
#            do sync op
#              websocket notify client go to minTime
#              client ready and stop and wait server notification
#              client send ready info
#              server send do play
#        else
#          skip;

