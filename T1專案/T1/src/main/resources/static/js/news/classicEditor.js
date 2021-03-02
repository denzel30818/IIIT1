ClassicEditor
   			.create( document.querySelector( '#editor' ),{
   			mediaEmbed:{
   				previewsInData:true
   			},
		    cloudServices: {
            tokenUrl: 'http://localhost:8080',
            uploadUrl: 'https://78133.cke-cs.com/easyimage/upload/'
        	}
   			} )
   			.then( editor => {
   		    console.log( editor );
   			} )
   			 .catch( error => {
        	console.error( error );
    		} );