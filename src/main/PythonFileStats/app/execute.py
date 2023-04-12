import sys

try:
    str = f"s.{sys.argv[1]}('"
    for a in sys.argv[2:]:
        str = f"{str}{a}','"
    if len(sys.argv) >= 3:
        str = str[:len(str) - 2] + ")"
    else:
        str = str[:len(str) - 1] + ")"
    print(eval(str))
except Exception as e:
    print(e)
