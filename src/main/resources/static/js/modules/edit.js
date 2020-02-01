function sendEditItem(){
    var actualId = document.getElementById("actualId").innerHTML;
    var actualDate = document.getElementById("actualDate").value;
    var accountId = document.getElementById("accountId").selectedIndex;
    var place = document.getElementById("place").value;
    var city = document.getElementById("city").value;
    var categoryId = document.getElementById("categoryId").selectedIndex;
    var typeBasic = document.getElementById("typeBasic").innerHTML;
    if(typeBasic == "Charging"){
        var type = document.getElementById("typech").value;
        var value = document.getElementById("valuech").value;
    } else {
        var type = document.getElementById("typecr").value;
        var value = document.getElementById("valuecr").value;
    }
    var comment = document.getElementById("comment").value;
    var charging = 0.0;
    var crediting = 0.0;
    if(actualDate == "" || accountId == 0 || place == "" || city == "" || categoryId == 0 || value == 0){
        showDangerModal();
    } else {
        if(type == "Open this select menu"){
            showDangerModal();
        } else {
             if(type == "Crediting"){
                 crediting = value;
             } else {
                 charging = value;
             }
             let goodCategoryId = categoryId + 4;
             let url = "/edit-item/" + actualId;
             let sendingData = {actualDate: actualDate, accountId: accountId, place: place, city: city, categoryId: goodCategoryId, charging: charging, crediting: crediting, comment: comment};
             fetch( url, {
               body: JSON.stringify(sendingData),
               method:"POST",
               headers: {
                 "Content-Type": "application/json",
               }
             }).then(response => response.text())
               .then((body) => {
                  let answer = body;
                  console.log(answer);
                  window.location.href = 'http://localhost:8000/';
               });
        }
    }
}

function editItem(){
    var categoryIdBasic = document.getElementById("categoryIdBasic").innerHTML;
    var accountIdBasic = document.getElementById("accountIdBasic").innerHTML;
    var typeBasic = document.getElementById("typeBasic").innerHTML;
    var newCategory = document.getElementById("categoryId");
    let trueId = categoryIdBasic - 4;
    newCategory.selectedIndex = trueId;
    var newAccount = document.getElementById("accountId");
    newAccount.selectedIndex = accountIdBasic;
    if(typeBasic == "Charging"){
        var type = document.getElementById("typech");
        type.selectedIndex = 1;
    } else {
        var type = document.getElementById("typecr");
        type.selectedIndex = 2;
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

editItem();