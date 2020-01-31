function sendNewItem1(){
    var actualDate = document.getElementById("actualDate1").value;
    var accountId = document.getElementById("accountId1").selectedIndex;
    var place = document.getElementById("place1").value;
    var city = document.getElementById("city1").value;
    var categoryId = document.getElementById("categoryId1").selectedIndex;
    var type = document.getElementById("type1").value;
    var value = document.getElementById("value1").value;
    var comment = document.getElementById("comment1").value;
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
             let sendingData = {actualDate: actualDate, accountId: accountId, place: place, city: city, categoryId: goodCategoryId, charging: charging, crediting: crediting, comment: comment};
             fetch( "/add-new-item-data", {
               body: JSON.stringify(sendingData),
               method:"POST",
               headers: {
                 "Content-Type": "application/json",
               }
             }).then(response => response.text())
               .then((body) => {
                  let answer = body;
                  console.log(answer);
                  location.reload();
               });
        }
    }
}

function showDangerModal(){
    $('#dangerModal').modal('show');
}

function closeDangerModal(){
     $('#dangerModal').modal('hide');
}