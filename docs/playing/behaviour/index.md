---
title: Changing Behaviour
---


# Behaviour editing

---


## Changing the behaviour

???+ "Getting UUID"
    An easy way to get the uuid of a Taterzen is to have your crosshairs focused on the Taterzen when typing out the command. Then, you can use tab completion and the uuid will be suggested.

Default Taterzen behaviour will be [passive](./passive.md).
*~~It's up to you whether you dare to change it.~~*

If you use any other types than the default one, you'd
probably want to change their invulnerable status as well.
To achieve that, use
```
/data merge entity <taterzen uuid> {Invulnerable:1b}
```

* 1b = true
* 0b = false

## Teaming them up

Taterzen teams use the vanilla teams system. For extra docs on the team command, see the [wiki](https://minecraft.fandom.com/wiki/Commands/team#Syntax).
To create and add taterzens to a team, you can simply do
```
/team add teamName
/team join teamName <taterzen uuid>
```

Taterzens **will never attack another entity on the same team**, but if you're not on their team, watch out!
