function addNewAccount(){
    var name = document.getElementById("name").value;
    if(name == ""){
        showDangerModal();
    } else {
        let url = "/add-new-account-data";
        let sendingData = {name: name};
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