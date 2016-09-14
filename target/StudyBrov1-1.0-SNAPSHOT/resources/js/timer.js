var myJsNumber = 52;
if(document.cookie && document.cookie.match('myClock')){
  // get deadline value from cookie
  deadlineMinutes = document.cookie.match(/(^|;)myClock=([^;]+)/)[2];
  //timeObject = document.cookie.match(/(^|;)myTimeObject=([^;]+)/)[2];
  console.log("Found cookie...");
  var mydata = JSON.parse(read_cookie('myObject'));
  var timeObject = new TimeObject(mydata.namevar, mydata.deadline);
  console.log(deadlineMinutes);
  console.log(timeObject.namevar);
  initializeClock(deadlineMinutes);
}
else{
  // create deadline 10 minutes from now
  console.log("Creating a deadline 10 min from now...");
  var timeInMinutes = 10;
  var currentTime = Date.parse(new Date());
  deadlineMinutes = new Date(currentTime + timeInMinutes*60*1000);
  initializeClock(deadlineMinutes);
  // store deadline in cookie for future reference
  document.cookie = 'myClock=' + deadlineMinutes + '; path=/;';
  var myTimeObject = new TimeObject("Espen", new Date())
  bake_cookie("myObject", myTimeObject.dumpData());
  //document.cookie = 'myTimeObject=' + myTimeObject + '; path=/;';
}
function readIntervalCookie() {
    var intervallObjectData = read_cookie("intervallObject");
    console.log(intervallObjectData.secondsOn);
}
function bake_cookie(name, value) {
  console.log("Baking" + value);
  var cookie = [name, '=', JSON.stringify(value),'; path=/;'].join('');
  document.cookie = cookie;
}

function read_cookie(name) {
    var result = document.cookie.match(new RegExp(name + '=([^;]+)'));
    console.log(result[1]);
    return JSON.parse(result[1]);
}
function TimeObject(namevar, deadline) {
    this.namevar = namevar;
    console.log("Just created a timeObject");
    this.deadline = deadline;
    this.dumpData = function() {
        return {
        'timeObject': {
            namevar: this.namevar,
            deadline: this.deadline
         }
       }
   }
}
function IntervallObject(startTime, endTime, intervalls, secondsOn, secondsOff) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.intervalls = intervalls;
    this.secondsOn = secondsOn;
    this.secondsOff = secondsOff;
}
function getTimeRemaining(deadline) {
  var t = Date.parse(deadline) - Date.parse(new Date());
  //var t = document.getElementById("minutes").value * 1000*60;
  var seconds = Math.floor((t / 1000) % 60);
  var minutes = Math.floor((t / 1000 / 60) % 60);
  var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
  var days = Math.floor(t / (1000 * 60 * 60 * 24));
  return {
    'total': t,
    'days': days,
    'hours': hours,
    'minutes': minutes,
    'seconds': seconds
  };
}
function initializeClock(deadline){
  var clock = document.getElementById("clockdiv");
  var daysSpan = clock.querySelector('.days');
  var hoursSpan = clock.querySelector('.hours');
  var minutesSpan = clock.querySelector('.minutes');
  var secondsSpan = clock.querySelector('.seconds');
  var timeinterval = setInterval(function(){
    var t = getTimeRemaining(deadline);
    daysSpan.innerHTML = t.days;
    hoursSpan.innerHTML = t.hours;
    minutesSpan.innerHTML = t.minutes;
    secondsSpan.innerHTML = t.seconds;
 //   clock.innerHTML = 'days: ' + t.days + '<br>' +
   //                   'hours: '+ t.hours + '<br>' +
     //                 'minutes: ' + t.minutes + '<br>' +
       //               'seconds: ' + t.seconds;
    if(t.total<=0){
      clearInterval(timeinterval);
    }
  },1000);
}
function setDeadline(){
    console.log(document.getElementById("minutes").value);
    console.log(deadlineMinutes);
    document.getElementById("minutes").value = myJsNumber;
}
function updateDisplay() {
    console.log("Updating display...");
}
function startTimer() {
    var timeNow = new Date();
    var minutesOn = parseInt(document.getElementById("minutesOn").value);
    var secondsOn = parseInt(document.getElementById("secondsOn").value);
    var minutesOff = parseInt(document.getElementById("minutesOff").value);
    var secondsOff = parseInt(document.getElementById("secondsOff").value);
    var intervals = parseInt(document.getElementById("intervalls").value);
    var totalSecondsOn = intervals*(60*minutesOn+ secondsOn);
    var totalSecondsOff = intervals*(60*minutesOff+ secondsOff);
    var deadline = new Date(timeNow.getTime() + 1000*(totalSecondsOn + totalSecondsOff));
    initializeClock(deadline);
    bake_cookie("intervallObject", new IntervallObject(timeNow, deadline, intervals, totalSecondsOn, totalSecondsOff));
    console.log("Starting timer.... timeOn:timeOf" + totalSecondsOn + ":" + totalSecondsOff);
    console.log(deadline);
}
function resetTimer() {
    console.log("Resetting timer....");
}