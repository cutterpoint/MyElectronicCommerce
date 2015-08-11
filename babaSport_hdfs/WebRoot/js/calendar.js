
document.writeln('<div id=meizzDateLayer style="position: absolute; width: 142; height: 166; z-index: 9998; display: none">');
document.writeln('<span id=tmpSelectYearLayer  style="z-index: 9999;position: absolute;top: 2; left: 18;display: none"></span>');
document.writeln('<span id=tmpSelectMonthLayer style="z-index: 9999;position: absolute;top: 2; left: 75;display: none"></span>');
document.writeln('<table border=0 cellspacing=1 cellpadding=0 width=142 height=160 bgcolor=#000000 onselectstart="return false">');
document.writeln('  <tr><td width=142 height=23 bgcolor=#FFFFFF><table border=0 cellspacing=1 cellpadding=0 width=140 height=23>');
document.writeln('      <tr align=center><td width=20 align=center bgcolor=#808080 style="font-size:12px;cursor: hand;color: #FFD700" ');
document.writeln('        onclick="meizzPrevM()" title="鍓嶄竴鏈� Author=meizz><b Author=meizz>&lt;</b>');
document.writeln('        </td><td width=100 align=center style="font-size:12px;cursor:default" Author=meizz>');

document.writeln('        <span Author=meizz id=meizzYearHead onmouseover="style.backgroundColor=\'yellow\'" onmouseout="style.backgroundColor=\'white\'" title="鐐瑰嚮杩欓噷閫夋嫨骞翠唤" onclick="tmpSelectYearInnerHTML(this.innerText)" style="cursor: hand;"></span>&nbsp;骞�nbsp;<span');
document.writeln('         id=meizzMonthHead Author=meizz onmouseover="style.backgroundColor=\'yellow\'" onmouseout="style.backgroundColor=\'white\'" title="鐐瑰嚮杩欓噷閫夋嫨鏈堜唤" onclick="tmpSelectMonthInnerHTML(this.innerText)" style="cursor: hand;"></span>&nbsp;鏈�/td>');

document.writeln('        <td width=20 bgcolor=#808080 align=center style="font-size:12px;cursor: hand;color: #FFD700" ');
document.writeln('         onclick="meizzNextM()" title="鍚庝竴鏈� Author=meizz><b Author=meizz>&gt;</b></td></tr>');
document.writeln('    </table></td></tr>');
document.writeln('  <tr><td width=142 height=18 bgcolor=#808080>');
document.writeln('<table border=0 cellspacing=0 cellpadding=0 width=140 height=1 style="cursor:default">');
document.writeln('<tr align=center><td style="font-size:12px;color:#FFFFFF" Author=meizz>鏃�/td>');
document.writeln('<td style="font-size:12px;color:#FFFFFF" Author=meizz class="td1">涓�/td><td style="font-size:12px;color:#FFFFFF" Author=meizz>浜�/td>');
document.writeln('<td style="font-size:12px;color:#FFFFFF" Author=meizz>涓�/td><td style="font-size:12px;color:#FFFFFF" Author=meizz>鍥�/td>');
document.writeln('<td style="font-size:12px;color:#FFFFFF" Author=meizz>浜�/td><td style="font-size:12px;color:#FFFFFF" Author=meizz>鍏�/td></tr>');
document.writeln('</table></td></tr><!-- Author:F.R.Huang(meizz) http://www.meizz.com/ mail: meizz@hzcnc.com 2002-10-8 -->');
document.writeln('  <tr><td width=142 height=120>');
document.writeln('    <table border=0 cellspacing=1 cellpadding=0 width=140 height=120 bgcolor=#FFFFFF>');
var n=0; for (j=0;j<5;j++){ document.writeln (' <tr align=center>'); for (i=0;i<7;i++){
document.writeln('<td width=20 height=20 id=meizzDay'+n+' style="font-size:12px" Author=meizz onclick=meizzDayClick(this.innerText)></td>');n++;}
document.writeln('</tr>');}
document.writeln('      <tr align=center><td width=20 height=20 style="font-size:12px" id=meizzDay35 Author=meizz ');
document.writeln('         onclick=meizzDayClick(this.innerText)></td>');
document.writeln('        <td width=20 height=20 style="font-size:12px" id=meizzDay36 Author=meizz onclick=meizzDayClick(this.innerText)></td>');
document.writeln('        <td colspan=5 align=right Author=meizz><span onclick=closeLayer() style="font-size:12px;cursor: hand"');
document.writeln('         Author=meizz title="杩斿洖锛堜笉閫夋嫨鏃ユ湡锛�><u>鍏抽棴</u></span>&nbsp;</td></tr>');
document.writeln('    </table></td></tr><tr><td>');
document.writeln('        <table border=0 cellspacing=1 cellpadding=0 width=100% bgcolor=#FFFFFF>');
document.writeln('          <tr><td Author=meizz align=left><input Author=meizz type=button value="<<" title="鍓嶄竴骞� onclick="meizzPrevY()" ');
document.writeln('             onfocus="this.blur()" style="	cursor: hand;BACKGROUND-COLOR: #808080;BORDER-BOTTOM: #808080 1px outset; BORDER-LEFT: #808080 1px outset; BORDER-RIGHT: #808080 1px outset; BORDER-TOP: #808080 1px outset; FONT-SIZE: 12px; height: 20px;color: #FFD700; font-weight: bold"><input Author=meizz title="鍓嶄竴鏈� type=button ');
document.writeln('             value="<" onclick="meizzPrevM()" onfocus="this.blur()" style="cursor: hand;BACKGROUND-COLOR: #808080;BORDER-BOTTOM: #808080 1px outset; BORDER-LEFT: #808080 1px outset; BORDER-RIGHT: #808080 1px outset; BORDER-TOP: #808080 1px outset;font-size: 12px; height: 20px;color: #FFD700; font-weight: bold"></td><td ');
document.writeln('             Author=meizz align=center><input Author=meizz type=button value="閲嶇疆" onclick="meizzToday()" ');
document.writeln('             onfocus="this.blur()" title="鏄剧ず褰撳墠鏃堕棿" style="cursor: hand;BACKGROUND-COLOR: #808080;BORDER-BOTTOM: #808080 1px outset; BORDER-LEFT: #808080 1px outset; BORDER-RIGHT: #808080 1px outset; BORDER-TOP: #808080 1px outset;font-size: 12px; height: 20px;color: #FFFFFF; font-weight: bold"></td><td ');
document.writeln('             Author=meizz align=right><input Author=meizz type=button value=">" onclick="meizzNextM()" ');
document.writeln('             onfocus="this.blur()" title="鍚庝竴鏈� style="cursor: hand;BACKGROUND-COLOR: #808080;BORDER-BOTTOM: #808080 1px outset; BORDER-LEFT: #808080 1px outset; BORDER-RIGHT: #808080 1px outset; BORDER-TOP: #808080 1px outset;font-size: 12px; height: 20px;color: #FFD700; font-weight: bold"><input ');
document.writeln('             Author=meizz type=button value=" >>" title="鍚庝竴骞� onclick="meizzNextY()"');
document.writeln('             onfocus="this.blur()" style="cursor: hand;BACKGROUND-COLOR: #808080;BORDER-BOTTOM: #808080 1px outset; BORDER-LEFT: #808080 1px outset; BORDER-RIGHT: #808080 1px outset; BORDER-TOP: #808080 1px outset;font-size: 12px; height: 20px;color: #FFD700; font-weight: bold"></td>');
document.writeln('</tr></table></td></tr></table></div>');


var outObject;
function setday(tt,obj) //涓昏皟鍑芥暟
{
  if (arguments.length >  2){alert("未知的世界");return;}
  if (arguments.length == 0){alert("未知的世界");return;}
  var dads  = document.all.meizzDateLayer.style;var th = tt;
  var ttop  = tt.offsetTop;     //TT鎺т欢鐨勫畾浣嶇偣楂�
  var thei  = tt.clientHeight;  //TT鎺т欢鏈韩鐨勯珮
  var tleft = tt.offsetLeft;    //TT鎺т欢鐨勫畾浣嶇偣瀹�
  var ttyp  = tt.type;          //TT鎺т欢鐨勭被鍨�
  while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
  dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+6;
  dads.left = tleft;
  outObject = (arguments.length == 1) ? th : obj;
  dads.display = '';
  event.returnValue=false;
}

var MonHead = new Array(12);    		   //瀹氫箟闃冲巻涓瘡涓湀鐨勬渶澶уぉ鏁�
    MonHead[0] = 31; MonHead[1] = 28; MonHead[2] = 31; MonHead[3] = 30; MonHead[4]  = 31; MonHead[5]  = 30;
    MonHead[6] = 31; MonHead[7] = 31; MonHead[8] = 30; MonHead[9] = 31; MonHead[10] = 30; MonHead[11] = 31;

var meizzTheYear=new Date().getFullYear(); //瀹氫箟骞寸殑鍙橀噺鐨勫垵濮嬪�
var meizzTheMonth=new Date().getMonth()+1; //瀹氫箟鏈堢殑鍙橀噺鐨勫垵濮嬪�
var meizzWDay=new Array(37);               //瀹氫箟鍐欐棩鏈熺殑鏁扮粍

document.onclick=function() //浠绘剰鐐瑰嚮鏃跺叧闂鎺т欢
{ 
  with(window.event.srcElement)
  { if (tagName != "INPUT" && getAttribute("Author")==null)
    document.all.meizzDateLayer.style.display="none";
  }
}

function meizzWriteHead(yy,mm)  //寰�head 涓啓鍏ュ綋鍓嶇殑骞翠笌鏈�
  { document.all.meizzYearHead.innerText  = yy;
    document.all.meizzMonthHead.innerText = mm;
  }

function tmpSelectYearInnerHTML(strYear) //骞翠唤鐨勪笅鎷夋
{
  if (strYear.match(/\D/)!=null){alert("未知的世界");return;}
  var m = (strYear) ? strYear : new Date().getFullYear();
  if (m < 1000 || m > 9999) {alert("未知的世界");return;}
  var n = m - 10;
  if (n < 1000) n = 1000;
  if (n + 26 > 9999) n = 9974;
  var s = "<select Author=meizz name=tmpSelectYear style='font-size: 12px' "
     s += "onblur='document.all.tmpSelectYearLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectYearLayer.style.display=\"none\";"
     s += "meizzTheYear = this.value; meizzSetDay(meizzTheYear,meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = n; i < n + 26; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option value='" + i + "' selected>" + i + "未知的世界" + "</option>\r\n";}
    else {selectInnerHTML += "<option value='" + i + "'>" + i + "未知的世界" + "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  document.all.tmpSelectYearLayer.style.display="";
  document.all.tmpSelectYearLayer.innerHTML = selectInnerHTML;
  document.all.tmpSelectYear.focus();
}

function tmpSelectMonthInnerHTML(strMonth) //鏈堜唤鐨勪笅鎷夋
{
  if (strMonth.match(/\D/)!=null){alert("未知的世界");return;}
  var m = (strMonth) ? strMonth : new Date().getMonth() + 1;
  var s = "<select Author=meizz name=tmpSelectMonth style='font-size: 12px' "
     s += "onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";"
     s += "meizzTheMonth = this.value; meizzSetDay(meizzTheYear,meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = 1; i < 13; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option value='"+i+"' selected>"+i+"未知的世界"+"</option>\r\n";}
    else {selectInnerHTML += "<option value='"+i+"'>"+i+"未知的世界"+"</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  document.all.tmpSelectMonthLayer.style.display="";
  document.all.tmpSelectMonthLayer.innerHTML = selectInnerHTML;
  document.all.tmpSelectMonth.focus();
}

function closeLayer()               //杩欎釜灞傜殑鍏抽棴
  {
    document.all.meizzDateLayer.style.display="none";
  }

document.onkeydown=function()
  {
    if (window.event.keyCode==27)document.all.meizzDateLayer.style.display="none";
  }

function IsPinYear(year)            //鍒ゆ柇鏄惁闂板钩骞�
  {
    if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
  }

function GetMonthCount(year,month)  //闂板勾浜屾湀涓�9澶�
  {
    var c=MonHead[month-1];if((month==2)&&IsPinYear(year)) c++;return c;
  }

function GetDOW(day,month,year)     //姹傛煇澶╃殑鏄熸湡鍑�
  {
    var dt=new Date(year,month-1,day).getDay()/7; return dt;
  }

function meizzPrevY()  //寰�墠缈�Year
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear--;}
    else{alert("骞翠唤瓒呭嚭鑼冨洿锛�000-9999锛夛紒");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextY()  //寰�悗缈�Year
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear++;}
    else{alert("骞翠唤瓒呭嚭鑼冨洿锛�000-9999锛夛紒");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzToday()  //Today Button
  {
    meizzTheYear = new Date().getFullYear();
    meizzTheMonth = new Date().getMonth()+1;
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzPrevM()  //寰�墠缈绘湀浠�
  {
    if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextM()  //寰�悗缈绘湀浠�
  {
    if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }

function meizzSetDay(yy,mm)   //涓昏鐨勫啓绋嬪簭**********
{
  meizzWriteHead(yy,mm);
  for (var i = 0; i < 37; i++){meizzWDay[i]=""};  //灏嗘樉绀烘鐨勫唴瀹瑰叏閮ㄦ竻绌�
  var day1 = 1,firstday = new Date(yy,mm-1,1).getDay();  //鏌愭湀绗竴澶╃殑鏄熸湡鍑�
  for (var i = firstday; day1 < GetMonthCount(yy,mm)+1; i++){meizzWDay[i]=day1;day1++;}
  for (var i = 0; i < 37; i++)
  { var da = eval("document.all.meizzDay"+i)     //涔﹀啓鏂扮殑涓�釜鏈堢殑鏃ユ湡鏄熸湡鎺掑垪
    if (meizzWDay[i]!="")
      { da.innerHTML = "<b>" + meizzWDay[i] + "</b>";
        da.style.backgroundColor = (yy == new Date().getFullYear() &&
        mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ? "#FFD700" : "#73a6de";
        da.style.cursor="hand"
      }
    else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default"}
  }
}
function meizzDayClick(n)  //鐐瑰嚮鏄剧ず妗嗛�鍙栨棩鏈燂紝涓昏緭鍏ュ嚱鏁�************
{
  var yy = meizzTheYear;
  var mm = meizzTheMonth;
  if (mm < 10){mm = "0" + mm;}
  if (outObject)
  {
    if (!n) {outObject.value=""; return;}
    if ( n < 10){n = "0" + n;}
    outObject.value= yy + "-" + mm + "-" + n ; //娉細鍦ㄨ繖閲屼綘鍙互杈撳嚭鏀规垚浣犳兂瑕佺殑鏍煎紡
    closeLayer(); 
  }
  else {closeLayer(); alert("未知的世界");}
}
meizzSetDay(meizzTheYear,meizzTheMonth);

// -->
