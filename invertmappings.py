import sys
assert len(sys.argv) == 3

raise Exception("This was temporary and probably did not work.")

inp = sys.argv[1]
out = sys.argv[2]

with open(inp, "r") as fi:
    data = fi.readlines()

class_maps = {} # class name pairs {deobfuscated : obfuscated}
meth_maps = {} # { deobfuscated : [(deobfuscated, obfuscated, signature)] }
attr_maps = {} # { deobfuscated : [(deobfuscated, obfuscated)] }

current = None # last class name (deobfuscated)

for i in data:
    assert i.endswith("\n")
    i = i[:-1]
    if not i.startswith("\t"):
        if i == "":
            continue
        deob, ob = i.split(" ")

        # if this fails we need to implement function signature 'introspection'
        assert deob == ob

        class_maps[deob] = ob
        current = deob
        meth_maps[current] = []
        attr_maps[current] = []
        continue
    i = i[1:]

    assert current is not None

    # if this fails we need to implement a second layer of nesting
    assert not i.startswith("\t")

    args = i.split(" ")
    if len(args) == 2:
        deob, ob = args
        attr_maps[current].append((deob, ob))
    elif len(args) == 3:
        deob, param, ob = args
        meth_maps[current].append((deob, ob, param))
    else:
        print("warning:", args)
        assert len(args) == 0

with open(out, "w") as f:
    for deob, ob in class_maps.items():
        f.write(f"{ob} {deob}\n")
        for (adeob, aob) in attr_maps[deob]:
            f.write(f"\t{aob} {adeob}\n")
        for (mdeob, mob, mparam) in meth_maps[deob]:
            f.write(f"\t{mob} {mparam} {mdeob}\n")