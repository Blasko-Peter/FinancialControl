function sendNewItem(){
    var actualDate = document.getElementById("actualDate").value;
    var accountId = document.getElementById("accountId").selectedIndex;
    var place = document.getElementById("place").value;
    var city = document.getElementById("city").value;
    var categoryId = document.getElementById("categoryId").selectedIndex;
    var type = document.getElementById("type").value;
    var value = document.getElementById("value").value;
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

function cancel(){
    window.location.href = 'http://localhost:8000/';
}

function showDangerModal(){
    $('#dangerModal').modal('show');
}

function closeDangerModal(){
     $('#dangerModal').modal('hide');
}