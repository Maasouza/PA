
function submitForm(formID,valorDoBotaoEscolhido){
    document.getElementById(formID).botaoEscolhido.value=valorDoBotaoEscolhido;
    document.getElementById(formID).submit();
}

function submitFormResultado(formID,valorDoBotaoEscolhido,id){
    document.getElementById(formID).botaoEscolhido.value=valorDoBotaoEscolhido;
    document.getElementById(formID).idEscolhido.value=id;
    document.getElementById(formID).submit();
}

function submitFormPages(formID,valorBT,pgid){
    document.getElementById(formID).botaoEscolhido.value=valorBT;
    document.getElementById(formID).idPagina.value=pgid;
    document.getElementById(formID).submit();
}


