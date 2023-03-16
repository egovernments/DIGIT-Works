import 'package:flutter/material.dart';

class SkillsDropdown extends StatefulWidget {
  @override
  _SkillsDropdownState createState() => _SkillsDropdownState();
}

class _SkillsDropdownState extends State<SkillsDropdown> {
  List<String> skills = [
    'UNSKILLED.MULIA',
    'UNSKILLED.PLUMBER',
    'SKILLED.MULIA',
    'SKILLED.WORKSMAN',
    'SKILLED.MASON',
    'SEMISKILLED.WELDER',
    'SEMISKILLED.ELECTRICIAN',
  ];

  late Map<String, bool> values;

  @override
  void initState() {
    super.initState();
    values = Map.fromIterable(
      skills,
      key: (skill) => skill,
      value: (skill) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    // Group skills by category
    Map<String, List<String>> groupedSkills = {};
    for (String skill in skills) {
      String category = skill.split('.')[0];
      groupedSkills.putIfAbsent(category, () => []);
      groupedSkills[category]!.add(skill);
    }

    return SizedBox(
      width: MediaQuery.of(context).size.width / 3, // Set a fixed width here
      child: SingleChildScrollView(
        child: Column(
          children: groupedSkills.entries
              .map(
                (entry) => Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      entry.key.toUpperCase(),
                      style: const TextStyle(fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 8),
                    Column(
                      children: entry.value
                          .map(
                            (skill) => CheckboxListTile(
                              title: Text(skill.split('.')[1]),
                              value: values[skill],
                              onChanged: (value) {
                                setState(() {
                                  values[skill] = value!;
                                });
                              },
                              controlAffinity: ListTileControlAffinity.leading,
                            ),
                          )
                          .toList(),
                    ),
                  ],
                ),
              )
              .toList(),
        ),
      ),
    );
  }
}
