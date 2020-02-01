function sendEditAccount(){
    var actualUrl = window.location.href.split('/')
    var accountId = actualUrl[4];
    var name = document.getElementById("name").value;
    if(name == ""){
        showDangerModal();
    } else {
        let url = "/edit-account/" + accountId;
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
                window.location.href = 'http://localhost:8000/';
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