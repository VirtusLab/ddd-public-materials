## Case 2
### _Leavers_
@snap[midpoint span-100]
@ol
- Just in time events
- Event Scheduler!
@olend
@snap[end]


---
## Leavers
</br>
@quote[Whenever **Employee leaves**, an **Effective Discount** should be **disassociated**](Domain Expert)

---
## Leavers 
@quote[Whenever **Employee leaves**, an **Effective Discount** should be **disassociated**](Domain Expert)
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/leavers.png)


---
## Contexts dependency
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/contexts.png)

---
## Events for future
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/events_for_future.png)

---
## Retroactive event

<br/>
![](time-based-domain-events/modelling/.assets/diagrams/retroactive_event.png)

---
## Bi-temporality

<br/>
![](time-based-domain-events/modelling/.assets/diagrams/bi-temporality.png)

---
## Bi-temporality
### As At
</br>
![](time-based-domain-events/modelling/.assets/diagrams/bi_temporality.png)

@snap[south-west span-30 text-06 fragment]
@box[rounded bg-orange](Today </br> # 2020-04-01 </br>)
@snapend

@snap[south span-30 text-06 fragment]
@box[rounded bg-green](as at #What is employee state as at 2020-02-20?)
@snapend

@snap[south-east span-30 text-06 fragment]
@box[rounded bg-blue](as at #EmployeePromoted)
@snapend


---
## Bi-temporality
### As At
</br>
![](time-based-domain-events/modelling/.assets/diagrams/bi_temporality.png)

@snap[south-west span-30 text-06 fragment]
@box[rounded bg-orange](Today </br> # 2020-04-01 </br>)
@snapend

@snap[south span-30 text-06 fragment]
@box[rounded bg-green](as at #What is employee state as at 2020-03-20?)
@snapend

@snap[south-east span-30 text-06 fragment]
@box[rounded bg-blue](as at #EmployeePromoted)
@snapend

---
## Bi-temporality
### As At
</br>
![](time-based-domain-events/modelling/.assets/diagrams/bi_temporality.png)

@snap[south-west span-30 text-06 fragment]
@box[rounded bg-orange](Today </br> # 2020-06-01 </br>)
@snapend

@snap[south span-30 text-06 fragment]
@box[rounded bg-green](as at #What is employee state as at 2020-05-10?)
@snapend

@snap[south-east span-30 text-06 fragment]
@box[rounded bg-blue](as at #EmployeePromoted, EmployeeChangedDepartment)
@snapend


---
## Bi-temporality
### As Of
</br>
![](time-based-domain-events/modelling/.assets/diagrams/bi_temporality.png)

@snap[south-west span-30 text-06 fragment]
@box[rounded bg-orange](Today </br> # 2020-02-20 </br>)
@snapend

@snap[south span-30 text-06 fragment]
@box[rounded bg-green](as of #What is employee state as of 2020-05-20?)
@snapend

@snap[south-east span-30 text-06 fragment]
@box[rounded bg-blue](as of #EmployeePromoted)
@snapend

---
## Bi-temporality
### As Of
</br>
![](time-based-domain-events/modelling/.assets/diagrams/bi_temporality.png)

@snap[south-west span-30 text-06 fragment]
@box[rounded bg-orange](Today </br> # 2020-04-20 </br>)
@snapend

@snap[south span-30 text-06 fragment]
@box[rounded bg-green](as of #What is employee state as of 2020-05-20?)
@snapend

@snap[south-east span-30 text-06 fragment]
@box[rounded bg-blue](as of #EmployeePromoted, EmployeeChangedDepartment)
@snapend

---
@fa[brain fa-7x]

@snap[midpoint span-40 text-06 fragment]
Split brain
@snapend

---
## Adjust model
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/employee_changed_department.png)


---
## Adjust model
@snap[west]
<b>Occurred:</b> 2020-02-01
![](time-based-domain-events/modelling/.assets/diagrams/adjusted_model_1.png)
@snapend

@snap[east]
<b>Occurred:</b> 2020-05-01
![](time-based-domain-events/modelling/.assets/diagrams/adjusted_model_2.png)
@snapend


---
## ACL
Anti-corruption layer
<br/>
<br/>
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/acl.png)

---
## Event Scheduler
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/marked_as_leaver.png)
