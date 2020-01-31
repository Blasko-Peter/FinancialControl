function addNewCategory(){
    var name = document.getElementById("name").value;
    var type = document.getElementById("type").value;
    var isActive = true;
    if(name == "" || type == "Open this select menu"){
        showDangerModal();
    } else {
        let url = "/add-new-category-data";
        let sendingData = {name: name, type: type, isActive: isActive};
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
                location.reload();
            });
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