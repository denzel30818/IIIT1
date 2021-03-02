setTimeout('countdown()', 1000);

function countdown() {
	var s = document.getElementById('counter');
	s.innerHTML = s.innerHTML - 1;
	if (s.innerHTML == 0)
		window.location = 'http://localhost:8080/login';
	else
		setTimeout('countdown()', 1000);
}