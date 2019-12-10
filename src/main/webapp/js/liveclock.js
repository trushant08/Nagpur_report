///////////////////////////////////////////////////////////
// "Live Clock Advanced" script - Version 1.0
// By Mark Plachetta (astroboy@zip.com.au)
//
// Get the latest version at:
// http://www.zip.com.au/~astroboy/liveclock/
//
// Based on the original script: "Upper Corner Live Clock"
// available at:
// - Dynamic Drive (http://www.dynamicdrive.com)
// - Website Abstraction (http://www.wsabstract.com)
// ========================================================
// CHANGES TO ORIGINAL SCRIPT:
// - Gave more flexibility in positioning of clock
// - Added date construct (Advanced version only)
// - User configurable
// ========================================================
// Both "Advanced" and "Lite" versions are available free
// of charge, see the website for more information on the
// two scripts.
///////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////
/////////////// CONFIGURATION /////////////////////////////

// Set the text to display before the clock:
var mypre_text = "IST - ";

// Display the time in 24 or 12 hour time?
// 0 = 24, 1 = 12
var my12_hour = 1;

// How often do you want the clock updated?
// 0 = Never, 1 = Every Second, 2 = Every Minute
// If you pick 0 or 2, the seconds will not be displayed
var myupdate = 1;

// Display the date?
// 0 = No, 1 = Yes
var DisplayDate = 0;

/////////////// END CONFIGURATION /////////////////////////
///////////////////////////////////////////////////////////

// Global varibale definitions:

var dn = "";
var mn = "th";
var old = "";

// The following arrays contain data which is used in the clock's
// date function. Feel free to change values for Days and Months
// if needed (if you wanted abbreviated names for example).
var DaysOfWeek = new Array(7);
DaysOfWeek[0] = "Sun";
DaysOfWeek[1] = "Mon";
DaysOfWeek[2] = "Tue";
DaysOfWeek[3] = "Wed";
DaysOfWeek[4] = "Thu";
DaysOfWeek[5] = "Fri";
DaysOfWeek[6] = "Sat";

var MonthsOfYear = new Array(12);
MonthsOfYear[0] = "Jan";
MonthsOfYear[1] = "Feb";
MonthsOfYear[2] = "Mar";
MonthsOfYear[3] = "Apr";
MonthsOfYear[4] = "May";
MonthsOfYear[5] = "Jun";
MonthsOfYear[6] = "Jul";
MonthsOfYear[7] = "Aug";
MonthsOfYear[8] = "Sep";
MonthsOfYear[9] = "Oct";
MonthsOfYear[10] = "Nov";
MonthsOfYear[11] = "Dec";

// This array controls how often the clock is updated,
// based on your selection in the configuration.
var ClockUpdate = new Array(3);
ClockUpdate[0] = 0;
ClockUpdate[1] = 1000;
ClockUpdate[2] = 60000;

// The main part of the script:
function show_clock() {
    // Get all our date variables:
    var Digital = new Date();
    var day = Digital.getDay();
    var mday = Digital.getDate();
    var month = Digital.getMonth();
    var hours = Digital.getHours();
    var year = Digital.getFullYear();
    var minutes = Digital.getMinutes();
    var seconds = Digital.getSeconds();

    // Fix the "mn" variable if needed:
    if (mday == 1) {
        mn = "st";
    }
    else if (mday == 2) {
        mn = "nd";
    }
    else if (mday == 3) {
        mn = "rd";
    }
    else if (mday == 21) {
        mn = "st";
    }
    else if (mday == 22) {
        mn = "nd";
    }
    else if (mday == 23) {
        mn = "rd";
    }
    else if (mday == 31) {
        mn = "st";
    }

    // Set up the hours for either 24 or 12 hour display:
    if (my12_hour) {
        dn = "am";
        if (hours >= 12) {
            dn = "pm";
            hours = hours - 12;
        }
        if (hours == 0) {
            hours = 12;
        }
    } else {
        dn = "";
    }
    if (minutes <= 9) {
        minutes = "0"+minutes;
    }
    if (seconds <= 9) {
        seconds = "0"+seconds;
    }

    // This is the actual HTML of the clock. If you're going to play around
    // with this, be careful to keep all your quotations in tact.
    myclock = '';
    myclock += '<b>';
    myclock += mypre_text+'</b>';
    myclock += hours+':'+minutes;
    if ((myupdate < 2) || (myupdate == 0)) {
        myclock += ':'+seconds;
    }
    myclock += ' '+dn;
    if (DisplayDate) {
        myclock += ' on '+DaysOfWeek[day]+', '+mday+mn+' '+MonthsOfYear[month];
    }
		
    document.getElementById("dispClock").innerHTML = myclock;
    if (myupdate != 0) {
        setTimeout("show_clock()",ClockUpdate[myupdate]);
    }
}
