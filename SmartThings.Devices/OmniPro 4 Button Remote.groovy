/**
 *  Omni Pro Bridge - 4-Button Remote
 */
metadata {
	definition (name: "OmniPro 4 Button Remote", namespace: "taasss", author: "taasss") {
    	capability "capability.button"
    
    	attribute "numButtons", "STRING"
		command "setButtonPressed"
        attribute "lastUpdateDate", "string"
        attribute "lastUpdateTime", "string"
	}

	tiles(scale: 2){
		standardTile("contact", "device.contact", width: 4, height: 4) {
			state("closed", label:'${name}', icon:"st.contact.contact.closed", backgroundColor:"#79b821" /*, action: "open"*/)
			state("open", label:'${name}', icon:"st.contact.contact.open", backgroundColor:"#ffa81e" /*, action: "close"*/)
		}
        
		main "contact"
		details(["contact"])
	}
}

def setButtonPressed(button) {
	log.info "${device.displayName}.setButtonPressed(${button})"
    
	sendEvent(name: "numButtons", value: "1", displayed: false)
	sendEvent(name: "button", value: "button ${button}", isStateChange: true, data: [buttonNumber: button], descriptionText: "$device.displayName button $button was pushed", displayed: true)
    sendEvent(name: "lastUpdateDate", value: ((new Date()).format('yyyy-MM-dd', location.timeZone)), displayed: false)
    sendEvent(name: "lastUpdateTime", value:((new Date()).format('HH:mm:ss', location.timeZone)), displayed: false)
}
