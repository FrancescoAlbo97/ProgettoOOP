# ProgettoOOP
Progetto Esame Luglio 2019

L'applicazione presente nella repository  permette di ottenere dati a partire da un dataset contenuto in un file CSV, offrendo all'utente la possibilità di avere statistiche su essi,filtrarli nel modo voluto e soprattutto **fornire  statistiche su qusti ultimi**.

# Cosa rappresentano i dati

Sono dati provenienti  dal Osservatorio atmosferico CE situato in Ispra (Italia),che rappresentano la concentrazione di alcune molecole [ monossido di carbonio (CO), ozono (O3), biossido di zolfo (SO2), monossido di azoto (NO), biossido di azoto (NO2) e altri ossidi di azoto (NOx)] nell'atmosfera calcolati durante l'anno 2016 ogni 10 minuti di ogni giorno. **Ci siamo accorti che alcuni dati erano non coerenti  con gli altri, forse a causa di errori durante le misurazioni, quindi al fine di ottenere statistiche giuste abbiamo deciso di non tener conto di essi**.

# Come vengono restituiti i dati
 
I dati vengono restituiti, grazie al servizio , come un  **JSON** , in particolare un array di oggetti dove ogni oggetto ha la seguente forma:
**FOTOOOOOOOOOO METADATA**
In particolare l'utente può vedere da cosa è formato ogni oggetto anche richiedendo i metadati attraverso la **rotta** -->**GET/metadata** come spiegato in seguito. 

# Rotte dell'applicazione

Sono le varie vie che l'applicazione offre affinché svolga correttamente i task assegnati.
Vengono gestite nel nostro progetto dal JSONController e l'utente può fare 2 tipi di richieste: GET e POST.
## GET 


 - **/metadata** : restituisce i metadati relativi agli oggetti.
 - **/data**:restituisce tutti dati del dataset.
 - **/data?month=1,2,ecc&day=(1,2,ecc)&molecule=(no,no2,nox,so2,o3,co)  }**: mi permette di vedere solo dati relativi a determinati mesi,giorni o addirittura solo  molecole volute. Se nel  caso viene a mancare month,day o molecule l'applicazione considera che l'utente vuole vedere tutto.
 - **/statistics**: mi fa vedere le statistiche in generale.
 -  **/statistics?{month=(1, 2, ecc)&day=(1, 2, ecc) &molecule=(no, no2, nox, so2, o3, co )**: mi permette di vedere una statistica su un determinati mesi,giorni o addirittura per molecola. La mancanza di una dei parametri fa si che l'applicazione considera che deve fare la statistica su tutti i dati del dato mancante come si vede nell'esempio:**FOTOOOOOOOO**
## POST 

 - **/filter** :rotta che mi permette di fare i filtri su tutti i dati del dataset.
 - **/filter/statistics** permette di avere statistiche su dati filtrati.
#  STATISTICHE
Come richiesto dalle specifiche del progetto abbiamo implementato varie statistiche però **NON abbiamo  realizzato la statistica COUNT**, anche se richiesta, perché non aveva senso per i nostri dati. 
Vediamo alcuni esempi:

 -  **/statistics**:mostra le statistiche (media, minimo, massimo, dev. standard e somma) di ogni molecola di tutto l'anno:
.![enter image description here](https://lh3.googleusercontent.com/NSdfXqfINXgjmkXJEzEtZgZg1VSNTKprBoCJRTYGe_goqo6gWLazHzI0AvwHgU93BcIor1OpVwQ)
 - **/statistics?month=1,2&day=1&molecule=no**  è un esempio di richiesta di statistica solo sulla molecola chiesta, nei mesi e giorni scelti dall'utente,

![enter image description here](https://lh3.googleusercontent.com/1jYnXX0PuKahD5adRttHGxZR1WfZNn0_VJ2Q81U7tF0Jro4zcimaicYjdqfO9hR5d45P_WnTPG4)
 
 ovviamente se nella richiesta manca un elemento considera tutto, per esempio:
  --**/statistics?day=3&month=2** restituisce le statistiche di tutte le molecole di 3 febbraio.
 -- **/statistics?month=2&molecule=so2** restituisce le statistiche della molecola so2 di tutti i giorni di febbraio.
 -- **/statistics?day=2&molecule=so2** restituisce le statistiche della molecola so2 calcolate nel secondo giorno di ogni mese.
 
#  FILTRI
Come richiesto dalle specifiche del progetto abbiamo analizzato anche i filtri attraverso l'implementazione di :
 -  operatori logici **AND** e **OR** 
 - operatori condizionali  **between, greater e lower**

**NON abbiamo analizzato NOT , NIN, IN e EQUAL** a causa del tipo di dati contenuti nel Dataset (non avevano senso).
La struttura del filtro è la seguente:
			

 - **{ < operatore logico > [{< filtro1 >},{< filtro2 >}, {< ecc >} }**
 
 Ogni **< filtro>** può avere altri operatori logici al suo interno, ognuno dei quali può a sua volta averne altri (vedere esempio)



Esempi:

- Filtro che prende solo gli elementi che hanno in comune contemporaneamente la  molecola di **no** ,con valore minore di 10.55, e quelle di **no2** ,maggiore di 16.25.

![enter image description here](https://lh3.googleusercontent.com/CMemlaBWD90v0HBs6QNPvbUaVpqqWlItXZJWkSZZ6iQMFhct4Dwy03muFkUCGNX8y10tgSDIHkk)

- Filtro che prende sia gli elementi che hanno la  molecola di **no** ,con valore compreso tra 10.4 e 10.55, sia quella di **no2**, tra 15.95 e 16.25 ,ovviamente presi una sola volta.

![enter image description here](https://lh3.googleusercontent.com/KLbvYtBPQoPdE6YJUQO9FvOo0uZs9uMIZcnJvk1h25F0XR4mzsM5LIbH_3gNPOayYNEWXuxcKT0)

-  Filtro che prende sia gli elementi che hanno la  molecola di **no** ,con valore compreso tra 10.4 e 10.55, sia quella di **no2**, tra 15.95 e 16.25, sia quelli in cui la **data** è minore di quella indicata,ovviamente una sola volta.

![enter image description here](https://lh3.googleusercontent.com/4LtFlVkbjaDcmiWDQJzI8vdG-oiAnuDGBpDH3-PgnzyU6_z0qump4akSXJb9GD1Nfmf2S2-pwlQ)

- filtro che prende tutti gli elementi con  valori di **no** minori di 10.55 insieme a quelli che hanno contemporaneamente i valori di **no2** maggiori di 16.25 e quelli di **no**  maggiori di 10.

![enter image description here](https://lh3.googleusercontent.com/YAjuEsxK7ySwhWiNubIixTMpk1J-KQ5ePRlIkj9Morm64t-tT7B55QOA6FOvYE34VJ7Vk56nYiw)

#  Statistiche su Filtri
L'applicazione offre anche la possibilità di calcolare le statistiche in base ai filtri chiesti dall'utente.
Effettuare una richiesta POST sulla  rotta **/filter/statistics?day=1** equivale a richiedere le statistiche sulle molecole del primo giorno di ogni mese, su dati precedentemente filtrati a volontà del utente;
si può osservare in figura che il risultato della rotta  **/statistics?day=1** è diverso da quello sui dati filtrati.

![enter image description here](https://lh3.googleusercontent.com/GZbuENaOs8gBAC8Xuls13ihDQyl70pt9n0rJK6O-IaiX-9bjcqa-OOXDGchkciXpm7FFRP1R0Zo)
#  UML
##  Class Diagram
Fornisce una vista strutturale del sistema.I blocchi rappresentano i Package e all'interno ci sono le classi che vi  appartengono,ognuna con i propri metodi e attributi.
![enter image description here](classdiagram2.png)


##  Use Case Diagram
Spiega le funzioni che l'applicazione offre all'utente. 
![enter image description here](UseCaseDiagram.PNG) 

##  Activity Diagram
Descrive le funzioni offerte dall'applicazione.  
![enter image description here](ActivityDiagram.PNG)

##  Sequence Diagram
Utilizzato per descrivere un'azione,ovvero una determinata sequenza di eventi, che portano al risultato richiesto dall'utente.  
L'applicazione ha i seguenti diagrammi di sequenza  per quanto riguarda le funzioni utilizzate nel **JSONController**:

- **getMetadata**
![enter image description here](6SequenceMetadataDiagram.PNG)

- **getData** 
![enter image description here](1sequenzeDataDiagram.PNG)

- **getStatistics()** 
![enter image description here](5SequenceStatisticsDiagram.PNG)

- **getFilteredValues()** 
![enter image description here](3SequenceFilterDiagram.PNG)

- **getStatisticsOfFilteredValues()** 
![enter image description here](4SequencefilterstatisticsDiagram.PNG)

- **Download and Parsing**
![enter image description here](2SequenceDownloadParsDiagram.PNG)
 
