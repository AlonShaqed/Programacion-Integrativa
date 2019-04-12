#! /usr/bin/python3
from client import *
from code import *
from decode import *
from csv import list_to_csv
from appJar import gui ##AppJar es requisito. Descargue en http://appjar.info si no esta instalado

#==============GUI functs===========
def actualizar(label):
	sensor = app.getEntry("esensor")
	data = app.getEntry("edata")
	if sensor == "" or data is None:
		app.errorBox("Input", "Uno o más campos están vacíos")
	else:
		toSend = codeUpdateString(sensor, data)
		if toSend is not False:
			send_to_socket(toSend)
			app.clearAllEntries()
		else:
			app.errorBox("Codificación", "El mensaje no se codificó.\nRevise que los datos no excedan el límite de caracteres")

def observar(label):
	observer = app.getEntry("eobserver")
	sensor = app.getEntry("eosensor")
	if observer == "" or sensor == "":
		app.errorBox("Input", "Uno o más campos están vacíos")
	else:
		toSend = codeObserverString(observer, sensor)
		if toSend is not False:
			send_to_socket(toSend)
			packet = send_and_receive_to_socket("Waiting...").decode("utf-8")
			parsed = list_to_csv(decodeUpdateString(packet)[:-1])
			if "notfound" in parsed:
				app.setLabel("lparsed", "Not found")
			else:
				app.setLabel("lparsed", parsed)
			app.clearAllEntries()
		else:
			app.errorBox("Codificación", "El mensaje no se codificó.\nRevise que los datos no excedan el límite de caracteres")


#===============GUI=================
app = gui("Cliente")

app.startTabbedFrame("tabs")
app.startTab("Actualizacion")
app.addLabel("lsensor", "Sensor <8 b>:")
app.addEntry("esensor")
app.addLabel("ldata", "Dato (#) <8 b>:")
app.addNumericEntry("edata")
app.addButton("Actualizar", actualizar)
app.stopTab()

app.startTab("Consulta")
app.addLabel("lobserver", "Observador <8 b>:")
app.addEntry("eobserver")
app.addLabel("losensor", "Sensor <8 b>:")
app.addEntry("eosensor")
app.addLabel("lparsed", "")
app.setLabelBg("lparsed", "yellow")
app.addButton("Buscar", observar)
app.stopTab()
app.stopTabbedFrame()

app.go()