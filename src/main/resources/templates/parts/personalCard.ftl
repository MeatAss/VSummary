<#macro mainCard cardTitle cardsText=[]>
    <div id="mainCard" class="card mb-2">
        <div class="card-body">
            <h5 class="card-title">${cardTitle}</h5>

            <div class="card-text">
                <#list cardsText as cardText>
                    <div class="form-group row">
                        <label for="givenName" class="col-sm-2 col-form-label text-right">${cardText.text}</label>
                        <div class="col-sm-10 col-form-label">
                            <a href="#" id="${cardText.idA}" data-type="text" data-pk="1" data-url="${cardText.post}" data-title="Enter ${cardText.text}">${cardText.textA}</a>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</#macro>