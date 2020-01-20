function settingDate(){
    var actualYear = document.getElementById("yearId").value;
    var actualMonth = document.getElementById("monthId").selectedIndex;
    if(actualYear == "Open this select menu" || actualMonth == 0){
        showDangerModal();
    } else {
        window.location.href = 'http://localhost:8000/setting/' + actualYear + '/' + actualMonth;
    }
}

function cancel(){
    window.location.href = 'http://localhost:8000/';
}

function showDangerModal(){
    $('#dangerModal').modal('show');
}

function closeDangerModal(){
     $('#dangerModal').modal('hide');
}